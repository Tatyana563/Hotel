package com.hotel.events.model;

import com.hotel.model.dto.request.BookingRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomBookedEvent {
    private BookingRequest bookingRequest;
    private String hotelName;
}
