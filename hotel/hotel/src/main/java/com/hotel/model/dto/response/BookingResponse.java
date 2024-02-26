package com.hotel.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class BookingResponse {
    private Instant checkIn;
    private Instant checkOut;
    private String hotelName;
    private int roomId;
    private RequestStatus status;
}
