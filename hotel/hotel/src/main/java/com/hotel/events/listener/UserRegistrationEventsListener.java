package com.hotel.events.listener;

import com.hotel.events.model.UserConfirmedRegistrationEvent;
import com.hotel.events.model.UserRegisteredEvent;
import com.hotel.service.api.notification.UserRegistrationEmailService;
import com.hotel.service.notification.UserRegistrationEmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserRegistrationEventsListener {
    private final UserRegistrationEmailService userRegistrationEmailService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void listenUserRegisteredEvent(UserRegisteredEvent userRegistrationEvent) {
        log.info("User was sent email to the mailbox: {} with confirmation link to complete registration", userRegistrationEvent.getToken().getUser().getEmail());
        userRegistrationEmailService.sendEmailLinkConfirmation(userRegistrationEvent);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void listenUserConfirmedRegistrationEvent(UserConfirmedRegistrationEvent userConfirmedRegistrationEvent) {
        log.info("User email {} was successfully confirmed", userConfirmedRegistrationEvent.getEmail());
        userRegistrationEmailService.sendEmailSuccessfulConfirmation(userConfirmedRegistrationEvent);
    }
}
