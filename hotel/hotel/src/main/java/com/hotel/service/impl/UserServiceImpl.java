package com.hotel.service.impl;

import com.hotel.config.properties.RegistrationProperties;
import com.hotel.events.model.UserConfirmedRegistrationEvent;
import com.hotel.events.model.UserRegisteredEvent;
import com.hotel.exception_handler.exception.*;
import com.hotel.model.dto.request.AllowRequestDTO;
import com.hotel.model.dto.request.RegistrationRequest;
import com.hotel.model.dto.request.ShortRegistrationRequest;
import com.hotel.model.dto.response.NotifyAgainResponse;
import com.hotel.model.entity.Role;
import com.hotel.model.entity.User;
import com.hotel.model.entity.VerificationToken;
import com.hotel.repository.RoleRepository;
import com.hotel.repository.TokenRepository;
import com.hotel.repository.UserRepository;
import com.hotel.service.api.UserService;
import com.hotel.service.api.notification.UserRegistrationEmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    // private final Map<String, Long> userLastRequestTime = new ConcurrentHashMap<>();
//    private static final long REQUEST_INTERVAL = 15*60*1000;
    private final ApplicationEventPublisher publisher;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final RegistrationProperties registrationProperties;
    private final UserRegistrationEmailService userRegistrationEmailService;

    @Transactional
    @Override
    public User register(RegistrationRequest request) {
        User savedUser = null;
        //TODO: and is enabled-> UserAlreadyCreatedException(request.getEmail(), null); flag active - Front offers login page or reset password,
        // inactive-> check spam or go to endpoint to to resend token
        // not enabled->
        //TODO: remove login
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyCreatedException(request.getEmail(), userRepository.isEnabled(request.getEmail()));
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setSurname(request.getSurname());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        String requestRole = request.getRole();
        Role role = roleRepository.findByName(requestRole);
        if (role == null) {
            throw new RoleNotFoundException(requestRole);
        }
        role.setName(requestRole);
        user.setRole(role);
        VerificationToken verificationToken = createVerificationToken();

        user = userRepository.save(user);
        verificationToken.setUser(user);
        tokenRepository.save(verificationToken);

        UserRegisteredEvent registrationEvent = new UserRegisteredEvent(verificationToken);
        publisher.publishEvent(registrationEvent);
        return savedUser;
    }

    @Override
    public NotifyAgainResponse resendRegistrationTokenRequest(ShortRegistrationRequest request) {
        //TODO:
        //check if userExistsByEmail
        //check that user is not enabled
        // find token by user with such email : a) no token, allowRequest, regenerate
        //b) token exists not expired - > resend, check lastNotifyDate, if 10 min passed change
        // lastNotifyDate and resend if not send precise time when can call again
        //c) token exists but expired - >checkNotifyDate and regenerate

        Duration requestInterval = registrationProperties.getRequestInterval();
        LocalDateTime currentTime = LocalDateTime.now();
        VerificationToken dbVerificationToken = tokenRepository.findByUserEmail(request.getEmail());
        if (dbVerificationToken != null) {
            // user was previously created but not activated
            // Check if there are at least 5 minutes before expiration
            // TODO: use JAva time (instant, .isAfter)
            //TODO: move to app.properties,

            LocalDateTime lastNotificationTime = dbVerificationToken.getLastNotificationDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime expectedNotificationTime = currentTime.minus(requestInterval);
            boolean isRequestAllowed = lastNotificationTime.isBefore(expectedNotificationTime);

            if (isRequestAllowed) {
                LocalDateTime tokenExpirationTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(dbVerificationToken.getExpiryDate().getTime()),
                        TimeZone.getDefault().toZoneId());
                boolean isNotAboutToBeExpired = false;
                if (!tokenExpirationTime.isAfter(currentTime)) {
                    long timeBeforeExpiration = Duration.between(currentTime, tokenExpirationTime).toMillis();
                    Duration tokenOperationTime = registrationProperties.getTokenOperationTime();
                    isNotAboutToBeExpired = tokenOperationTime.minusMillis(timeBeforeExpiration).isNegative();

                    if (isNotAboutToBeExpired) {
                        //TODO: token, lastNotifDate compare to now, RequestInterval to appl.properties
                        UserRegisteredEvent registrationEvent = new UserRegisteredEvent(dbVerificationToken);
                        publisher.publishEvent(registrationEvent);
                        //TODO: not new DAte,
                        dbVerificationToken.setLastNotificationDate(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()));
                        tokenRepository.save(dbVerificationToken);
                        return new NotifyAgainResponse(true, currentTime.plus(requestInterval).atZone(ZoneId.systemDefault()).toInstant());

                    } else {
                        dbVerificationToken.setExpiryDate(calculateExpiryDate());
                        //TODO: check if allowed
                        dbVerificationToken.setLastNotificationDate(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()));
                        tokenRepository.save(dbVerificationToken);
                        UserRegisteredEvent registrationEvent = new UserRegisteredEvent(dbVerificationToken);
                        publisher.publishEvent(registrationEvent);
                        return new NotifyAgainResponse(true, currentTime.plus(requestInterval).atZone(ZoneId.systemDefault()).toInstant());
                    }
                } else {
                    VerificationToken verificationToken = createVerificationToken();
                    User dbUser = userRepository.findUserByEmail(request.getEmail());
                    //User was manually deleted from DB by mistake, a rare case
                    if (dbUser == null) {
                        throw new UserNotFoundException((request.getEmail()));
                    }
                    verificationToken.setUser(dbUser);
                    tokenRepository.save(verificationToken);
                    //TODO: simplify dbUser.getUsername(), dbUser.getEmail(), verificationToken - redundant username, is in token
                    UserRegisteredEvent registrationEvent = new UserRegisteredEvent(verificationToken);
                    publisher.publishEvent(registrationEvent);
                    return new NotifyAgainResponse(true, currentTime.plus(requestInterval).atZone(ZoneId.systemDefault()).toInstant());

                }
            } else {
                return new NotifyAgainResponse(false, currentTime.plus(requestInterval).atZone(ZoneId.systemDefault()).toInstant());
            }
        }
        // token in DB was expired and deleted by cron job;
        else {
            VerificationToken verificationToken = createVerificationToken();
            User dbUser = userRepository.findUserByEmail(request.getEmail());
            //User was manually deleted from DB by mistake, a rare case
            if (dbUser == null) {
                throw new UserNotFoundException((request.getEmail()));
            }
            verificationToken.setUser(dbUser);
            tokenRepository.save(verificationToken);
            //TODO: simplify dbUser.getUsername(), dbUser.getEmail(), verificationToken - redundant username, is in token
            UserRegisteredEvent registrationEvent = new UserRegisteredEvent(verificationToken);
            publisher.publishEvent(registrationEvent);
            return new NotifyAgainResponse(true, currentTime.plus(requestInterval).atZone(ZoneId.systemDefault()).toInstant());
        }

    }

    private VerificationToken createVerificationToken() {
        VerificationToken verificationToken = new VerificationToken();
        Date expiryDate = calculateExpiryDate();
        verificationToken.setExpiryDate(expiryDate);
        verificationToken.setLastNotificationDate(expiryDate);
        return verificationToken;
    }

    private Date calculateExpiryDate() {
        Duration tokenExpiration = registrationProperties.getTokenExpiration();
        return Date.from(Instant.now().plus(tokenExpiration));
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Timestamp(cal.getTime().getTime()));
//        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
//        return new Date(cal.getTime().getTime());

    }

    @Override
    public void confirmRegistration(UUID token) {
        VerificationToken verificationToken = tokenRepository.findById(token).orElseThrow(() -> new RegistrationNotFoundException("Token was not found: " + token.toString(), token.toString()));

        Date expiryDate = verificationToken.getExpiryDate();

        Date currentDate = new Date();

        boolean isTokenValid = expiryDate.after(currentDate);
        if (isTokenValid) {
            User user = verificationToken.getUser();
            user.setEnabled(true);
            userRepository.save(user);
            verificationToken.setUser(null);
            tokenRepository.delete(verificationToken);
            UserConfirmedRegistrationEvent event = new UserConfirmedRegistrationEvent(user.getUsername(), user.getEmail());
            publisher.publishEvent(event);
        } else throw new TokenExpirationException(token);

//TODO: user gets email about confirmation;
    }

    //TODO: add to DB table token lastNotificationTime, send user precise time when it is allowed to come again
//    private boolean allowRequest(String login) {
//        long currentTime = System.currentTimeMillis();
//
//        // If the user is making the first request or the last request was more than 1 minute ago
//        if (!userLastRequestTime.containsKey(login) || currentTime - userLastRequestTime.get(login) > REQUEST_INTERVAL) {
//            userLastRequestTime.put(login, currentTime);
//            return true;
//        }
//
//        return false;
//    }
//
//    private AllowRequestDTO allowRequest(String email) {
//        VerificationToken verificationToken = tokenRepository.findByUserEmail(email);
//        long currentTime = System.currentTimeMillis();
//        if (currentTime - verificationToken.getLastNotificationDate().getTime() > REQUEST_INTERVAL) {
//            return new AllowRequestDTO(true, 0);
//        }
//        long timeToWait = REQUEST_INTERVAL - (currentTime - verificationToken.getLastNotificationDate().getTime());
//        return new AllowRequestDTO(false, timeToWait);
//    }
}
