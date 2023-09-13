package com.hotel.service;

import com.hotel.confirmation.VerificationToken;
import com.hotel.events.model.UserRegistrationEvent;
import com.hotel.exception_handler.UserAlreadyCreated;
import com.hotel.model.dto.request.RegistrationRequest;
import com.hotel.model.entity.User;
import com.hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ApplicationEventPublisher publisher;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final String userVerificationUrl = "https://";

    @Transactional
    @Override
    public User register(RegistrationRequest request) {
        User savedUser = null;
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyCreated(request.getEmail());
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

        user.setVerificationToken(verificationToken);
        verificationToken.setUser(user);

        savedUser = userRepository.save(user);
        UserRegistrationEvent registrationEvent = new UserRegistrationEvent(request, verificationToken, userVerificationUrl);
        publisher.publishEvent(registrationEvent);
        return savedUser;
    }

    private VerificationToken createVerificationToken() {
        VerificationToken verificationToken = new VerificationToken();
        String token = UUID.randomUUID().toString();
        verificationToken.setToken(token);
        verificationToken.setExpiryDate(verificationToken.calculateExpiryDate(5));
        return verificationToken;
    }
}
