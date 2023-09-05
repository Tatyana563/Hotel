package com.hotel.model.dto.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BookingRequest {
    String username;
    String email;
    @DateTimeFormat(pattern = "dd_MM_yyyy")
    private Date checkIn;
    @DateTimeFormat(pattern = "dd_MM_yyyy")
    private Date checkOut;
}
