package com.hotel.service.notification;

import com.hotel.events.model.RoomBookedEvent;
import com.hotel.service.api.notification.BookingEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class BookingEmailServiceImpl implements BookingEmailService {
    private final JavaMailSender mailSender;
@Override
    public void sendRoomBookedEmail(RoomBookedEvent roomBookedEvent) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(roomBookedEvent.getBookingRequest().getEmail());
        message.setSubject("Booking");
        message.setText(formMessage(roomBookedEvent.getBookingRequest().getCheckIn(), roomBookedEvent.getBookingRequest().getCheckOut(),
                roomBookedEvent.getBookingRequest().getUsername(), roomBookedEvent.getHotelName()));

        mailSender.send(message);
    }

    private String formMessage(Date start, Date end, String username, String hotel) {
        return String.format("Hello, %s! You successfully booked a room in %s from %s to %s.", username, hotel, start, end);
    }
}
