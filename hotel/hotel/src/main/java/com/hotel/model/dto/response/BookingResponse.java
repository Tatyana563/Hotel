package com.hotel.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
public class BookingResponse {

    @DateTimeFormat(pattern = "dd_MM_yyyy")
    private Date checkIn;
    @DateTimeFormat(pattern = "dd_MM_yyyy")
    private Date checkOut;
    private String hotelName;
    private int roomId;
    private RequestStatus status;
}
