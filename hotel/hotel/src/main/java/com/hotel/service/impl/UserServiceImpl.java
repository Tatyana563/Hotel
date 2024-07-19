package com.hotel.service.impl;

import com.hotel.config.properties.RegistrationProperties;
import com.hotel.events.model.PasswordResetEvent;
import com.hotel.events.model.UserConfirmedRegistrationEvent;
import com.hotel.events.model.UserRegisteredEvent;
import com.hotel.exception_handler.exception.InvalidTokenException;
import com.hotel.exception_handler.exception.RoleNotFoundException;
import com.hotel.exception_handler.exception.UserAlreadyCreatedException;
import com.hotel.exception_handler.exception.UserNotFoundException;
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

import java.security.SecureRandom;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ApplicationEventPublisher publisher;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final RegistrationProperties registrationProperties;
    private final UserRegistrationEmailService userRegistrationEmailService;
    private final Clock clock;

    //   @Transactional
    @Override
    public User register(RegistrationRequest request) {


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
        return user;
    }

    @Override
    public NotifyAgainResponse resendRegistrationTokenRequest(ShortRegistrationRequest request) {
//TODO: all Instant, also in Entity
        //TODO:  Check serialization of time NotifyAgainResponse
        // unit test
        Duration requestRetryDuration = registrationProperties.getRequestRetryDuration();
        // LocalDateTime now = LocalDateTime.now();
        Instant now = Instant.now(clock);
        VerificationToken dbVerificationToken = tokenRepository.findByUserEmail(request.getEmail());

        if (dbVerificationToken == null) {
            return createNewTokenAndNotify(request, requestRetryDuration, now);
        }

        Instant lastNotificationTime = dbVerificationToken.getLastNotificationDate();
        Instant expectedNotificationTime = now.minus(requestRetryDuration);
        boolean isRequestAllowed = lastNotificationTime.isBefore(expectedNotificationTime);

        if (!isRequestAllowed) {
            return new NotifyAgainResponse(false, lastNotificationTime.plus(requestRetryDuration).atZone(ZoneId.systemDefault()).toInstant());
        }

        Instant tokenExpirationTime = dbVerificationToken.getExpiryDate();

        boolean tokenExpired = tokenExpirationTime.isBefore(now);

        if (tokenExpired) {
            return createNewTokenAndNotify(request, requestRetryDuration, now);
        }

        Instant timeToRenew = tokenExpirationTime.minus(registrationProperties.getTokenTimeLeftToRenew());
        boolean isAboutToBeExpired = now.isAfter(timeToRenew);

        if (isAboutToBeExpired || tokenExpirationTime.isBefore(lastNotificationTime.plus(requestRetryDuration))) {
            dbVerificationToken.setExpiryDate(calculateExpiryDate());
        }

        UserRegisteredEvent registrationEvent = new UserRegisteredEvent(dbVerificationToken);
        publisher.publishEvent(registrationEvent);

        dbVerificationToken.setLastNotificationDate(now);
        tokenRepository.save(dbVerificationToken);
        return new NotifyAgainResponse(true, now.plus(requestRetryDuration).atZone(ZoneId.systemDefault()).toInstant());

    }

    private NotifyAgainResponse createNewTokenAndNotify(ShortRegistrationRequest request, Duration requestRetryDuration, Instant now) {
        Optional<User> dbUser = userRepository.findUserByEmail(request.getEmail());
        //User was manually deleted from DB by mistake, a rare case
        if (dbUser == null) {
            throw new UserNotFoundException((request.getEmail()));
        }
        VerificationToken verificationToken = createVerificationToken();
        verificationToken.setUser(dbUser.get());
        tokenRepository.save(verificationToken);

        UserRegisteredEvent registrationEvent = new UserRegisteredEvent(verificationToken);
        publisher.publishEvent(registrationEvent);
        return new NotifyAgainResponse(true, now.plus(requestRetryDuration));
    }

    public VerificationToken createVerificationToken() {
        VerificationToken verificationToken = new VerificationToken();
        Instant expiryDate = calculateExpiryDate();
        verificationToken.setExpiryDate(expiryDate);
        verificationToken.setLastNotificationDate(expiryDate);
        return verificationToken;
    }

    private Instant calculateExpiryDate() {
        Duration tokenExpiration = registrationProperties.getTokenExpiration();
        Instant now = Instant.now(clock);
        return now.plus(tokenExpiration);

    }

    @Override
    public UserConfirmedRegistrationEvent confirmRegistration(UUID token) {
        VerificationToken verificationToken = tokenRepository.findById(token).orElseThrow(() -> new InvalidTokenException(token.toString()));

        Date expiryDate = Date.from(verificationToken.getExpiryDate());

        Date currentDate = new Date();

        boolean isTokenValid = expiryDate.after(currentDate);
        UserConfirmedRegistrationEvent event;
        if (isTokenValid) {
            User user = verificationToken.getUser();
            user.setEnabled(true);
            userRepository.save(user);
            verificationToken.setUser(null);
            tokenRepository.delete(verificationToken);
            event = new UserConfirmedRegistrationEvent(user.getUsername(), user.getEmail());
            publisher.publishEvent(event);
        }
        else throw new InvalidTokenException(String.valueOf(token));
        return event;
    }


    @Override
    public void reset(String email) {
        String resetToken = generateToken();
        PasswordResetEvent event = new PasswordResetEvent(resetToken, email);
        Optional<User> userByEmail = userRepository.findUserByEmail(email);
        if (userByEmail == null) {
            throw new UserNotFoundException(email);
        }
        userByEmail.get().setTokenReset(resetToken);
        userRepository.save(userByEmail.get());
        publisher.publishEvent(event);
    }

    @Override
    public void createNewPassword(String token, String password) {
        //TODO: use JWT token to check that it is valid;
        User user = userRepository.findByTokenReset(token);
        if (user == null) {
            throw new InvalidTokenException(token);
        }
        user.setPassword(passwordEncoder.encode(password));
        user.setTokenReset(null);
        userRepository.save(user);

    }


    public static String generateToken() {
        // Generate a secure random byte array
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);

        // Encode the byte array to Base64 to get a string representation
        String token = Base64.getEncoder().encodeToString(randomBytes);

        return token;
    }
}
