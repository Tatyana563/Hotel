package com.hotel.model.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@RequiredArgsConstructor
public class BookingRequest {
    String username;
    String email;
    private Instant checkIn;
    private Instant checkOut;
}
