package com.hotel.service.api.notification;

import com.hotel.events.model.RoomBookedEvent;

public interface BookingEmailService {
     void sendRoomBookedEmail(RoomBookedEvent roomBookedEvent);
}
