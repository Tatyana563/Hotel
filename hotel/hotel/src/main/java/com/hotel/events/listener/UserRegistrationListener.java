package com.hotel.events.listener;

import com.hotel.events.model.UserRegistrationEvent;
import com.hotel.service.UserRegistrationEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserRegistrationListener {
    private final UserRegistrationEmailService userRegistrationEmailService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void listenBookingEvent(UserRegistrationEvent userRegistrationEvent) {
        log.info("User was sent email with confirmation link to complete registration: {}", userRegistrationEvent);
        userRegistrationEmailService.sendEmail(userRegistrationEvent);
    }
}
