package com.hotel.events.listener;

import com.hotel.events.model.PasswordResetEvent;
import com.hotel.events.model.UserConfirmedRegistrationEvent;
import com.hotel.events.model.UserRegisteredEvent;
import com.hotel.service.api.notification.UserRegistrationEmailService;
import com.hotel.service.notification.PasswordResetServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class PasswordResetEventsListener {
    private final PasswordResetServiceImpl passwordResetService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void listenPasswordResetEvent(PasswordResetEvent passwordResetEvent) {
        log.info("User was sent email to reset password: {}", passwordResetEvent);
        passwordResetService.sendResetPasswordLink(passwordResetEvent);
    }

}
