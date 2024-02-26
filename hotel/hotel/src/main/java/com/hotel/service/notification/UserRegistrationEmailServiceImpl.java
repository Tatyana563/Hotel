package com.hotel.service.notification;

import com.hotel.config.properties.RegistrationProperties;
import com.hotel.events.model.UserConfirmedRegistrationEvent;
import com.hotel.events.model.UserRegisteredEvent;
import com.hotel.service.api.notification.UserRegistrationEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationEmailServiceImpl implements UserRegistrationEmailService {
    private final JavaMailSender mailSender;
    private final RegistrationProperties registrationProperties;

    @Override
    public void sendEmailLinkConfirmation(UserRegisteredEvent userRegistrationEvent) {
        String username = userRegistrationEvent.getToken().getUser().getUsername();
        String baseUrl = registrationProperties.getBaseUrl();
        String token = userRegistrationEvent.getToken().getId().toString();
        String message = String.format("Hello, %s! Please open the %s to finish the registration using your token: %s.", username, baseUrl, token);
        String email = userRegistrationEvent.getToken().getUser().getEmail();
        String userRegistration = "User registration";
        sendEmail(email, userRegistration, message);
    }

    @Override
    public void sendEmailSuccessfulConfirmation(UserConfirmedRegistrationEvent userConfirmedRegistrationEvent) {
        String subject = "Successful confirmation of registration";
        String message = "Hello %s! You have successfully confirmed the mail.";
        String email = userConfirmedRegistrationEvent.getEmail();
        sendEmail(email, subject, message);
    }

    private void sendEmail(String email, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

}
