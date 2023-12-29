package com.hotel.service.impl;

import com.hotel.config.properties.RegistrationProperties;
import com.hotel.events.model.UserConfirmedRegistrationEvent;
import com.hotel.events.model.UserRegisteredEvent;
import com.hotel.exception_handler.exception.RegistrationNotFoundException;
import com.hotel.exception_handler.exception.RoleNotFoundException;
import com.hotel.exception_handler.exception.TokenExpirationException;
import com.hotel.exception_handler.exception.UserAlreadyCreatedException;
import com.hotel.model.dto.request.RegistrationRequest;
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
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Map<String, Long> userLastRequestTime = new ConcurrentHashMap<>();
    private static final long REQUEST_INTERVAL = 60000;
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
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyCreatedException(request.getEmail(), null);
        }
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new UserAlreadyCreatedException(null, request.getLogin());
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setLogin(request.getLogin());
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

        UserRegisteredEvent registrationEvent = new UserRegisteredEvent(request.getUsername(), request.getEmail(), verificationToken);
        publisher.publishEvent(registrationEvent);
        return savedUser;
    }

    @Override
    public String resendRegistrationTokenRequest(String email) {

        VerificationToken verificationToken = tokenRepository.findByUserEmail(email);

        if (verificationToken != null) {
            //TODO : check time when token was send
            verificationToken.setExpiryDate(calculateExpiryDate());
            tokenRepository.save(verificationToken);
            UserRegisteredEvent registrationEvent = new UserRegisteredEvent(verificationToken.getUser().getUsername(), verificationToken.getUser().getEmail(), verificationToken);
            publisher.publishEvent(registrationEvent);
        }
        //TODO: not null
        return null;
    }


    private VerificationToken createVerificationToken() {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setExpiryDate(calculateExpiryDate());
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

    private boolean allowRequest(String login) {
        long currentTime = System.currentTimeMillis();

        // If the user is making the first request or the last request was more than 1 minute ago
        if (!userLastRequestTime.containsKey(login) || currentTime - userLastRequestTime.get(login) > REQUEST_INTERVAL) {
            userLastRequestTime.put(login, currentTime);
            return true;
        }

        return false;
    }
}