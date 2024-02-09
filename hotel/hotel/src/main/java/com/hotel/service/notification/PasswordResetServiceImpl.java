package com.hotel.service.notification;

import com.hotel.config.properties.RegistrationProperties;
import com.hotel.events.model.PasswordResetEvent;
import com.hotel.events.model.UserConfirmedRegistrationEvent;
import com.hotel.events.model.UserRegisteredEvent;
import com.hotel.service.api.notification.PasswordResetService;
import com.hotel.service.api.notification.UserRegistrationEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {
    private final JavaMailSender mailSender;
    private final RegistrationProperties registrationProperties;

    private void sendEmail(String email, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Override
    public void sendResetPasswordLink(PasswordResetEvent passwordResetEvent) {
        String email = passwordResetEvent.getEmail();
        String token = passwordResetEvent.getResetToken();
        //Redirect to front page
        String url = registrationProperties.getBaseUrl()+"/reset/"+token;
        String message = String.format("Please open the  %s to change password", url);

        String passwordReset = "Password reset";
        sendEmail(email, passwordReset, message);
    }
}
