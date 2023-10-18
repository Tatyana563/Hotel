package com.hotel.service;

import com.hotel.config.properties.RegistrationProperties;
import com.hotel.events.model.UserConfirmedRegistrationEvent;
import com.hotel.events.model.UserRegisteredEvent;
import com.hotel.exception_handler.RegistrationNotFoundException;
import com.hotel.exception_handler.TokenExpirationException;
import com.hotel.exception_handler.UserAlreadyCreated;
import com.hotel.model.dto.request.RegistrationRequest;
import com.hotel.model.entity.User;
import com.hotel.model.entity.VerificationToken;
import com.hotel.repository.TokenRepository;
import com.hotel.repository.UserRepository;
import com.hotel.service.api.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private final ApplicationEventPublisher publisher;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RegistrationProperties registrationProperties;

    @Transactional
    @Override
    public User register(RegistrationRequest request) {
        User savedUser = null;
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyCreated(request.getEmail(),null);
        }
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new UserAlreadyCreated(null,request.getLogin());
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setLogin(request.getLogin());
        user.setSurname(request.getSurname());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());

        VerificationToken verificationToken = createVerificationToken();

        user = userRepository.save(user);
        verificationToken.setUser(user);
        tokenRepository.save(verificationToken);

        UserRegisteredEvent registrationEvent = new UserRegisteredEvent(request, verificationToken);
        publisher.publishEvent(registrationEvent);
        return savedUser;
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
        VerificationToken verificationToken = tokenRepository.findById(token)
                .orElseThrow(() -> new RegistrationNotFoundException(token));

        Date expiryDate = verificationToken.getExpiryDate();

        Date currentDate = new Date();

        boolean isTokenValid = expiryDate.after(currentDate);
        if (isTokenValid) {
            User user = verificationToken.getUser();
            user.setEnabled(true);
            userRepository.save(user);
            tokenRepository.delete(verificationToken);
            UserConfirmedRegistrationEvent event = new UserConfirmedRegistrationEvent(user.getUsername(), user.getEmail());
            publisher.publishEvent(event);
        }
        else throw new TokenExpirationException(token);

//TODO: user gets email about confirmation;
    }
}
