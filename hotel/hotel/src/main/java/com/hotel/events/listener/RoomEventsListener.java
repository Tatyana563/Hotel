package com.hotel.events.listener;

import com.hotel.events.model.RoomBookedEvent;
import com.hotel.service.api.notification.BookingEmailService;
import com.hotel.service.notification.BookingEmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoomEventsListener {
    private final BookingEmailService bookingEmailService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void listenBookingEvent(RoomBookedEvent roomBookedEvent) {
        log.info("Received RoomBookedEvent: {}", roomBookedEvent);
        bookingEmailService.sendRoomBookedEmail(roomBookedEvent);
    }
}
