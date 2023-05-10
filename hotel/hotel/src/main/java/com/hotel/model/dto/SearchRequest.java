package com.hotel.model.dto;

import com.hotel.model.enumeration.RoomType;
import com.hotel.model.enumeration.StarRating;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class SearchRequest {

    private StarRating starRating;
    private RoomType roomType;
    @DateTimeFormat(pattern = "dd_MM_yyyy")
    private Date checkIn;
    @DateTimeFormat(pattern = "dd_MM_yyyy")
    private Date checkOut;
}
