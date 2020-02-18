package com.hotel.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailServile {

    private final JavaMailSender javaMailSender;
//dont forget to add @EnableAsync to HotelInitialiser to create  a separate thread
// for sending emails
    @Override
    @SneakyThrows
    @Async
    public void sendLoginAndPass(String name, String pass, boolean created) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        // helper.setText("Your password" + userEntity.getPassword());
        helper.setText(String.format("Name %s password %s", name, pass));
        //  helper.setText("Your password" + userEntity.getName());
        helper.setTo("kornushkova56@gmail.com");
        helper.setSubject(created ? "New user is created" : "New user is updated");
        javaMailSender.send(mimeMessage);
    }
}
