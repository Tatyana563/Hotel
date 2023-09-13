package com.hotel.service;

import com.hotel.events.model.UserRegistrationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationEmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(UserRegistrationEvent userRegistrationEvent) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userRegistrationEvent.getRequest().getEmail());
        message.setSubject("User registration");
        message.setText(formMessage(userRegistrationEvent.getRequest().getUsername(), userRegistrationEvent.getVerificationUrl(), userRegistrationEvent.getToken().getToken()));

        mailSender.send(message);
    }

    private String formMessage(String name, String confirmationUrl, String token) {
        return String.format("Hello, %s! Please open the %s to finish the registration using your token: %s.", name, confirmationUrl, token);

    }
}
