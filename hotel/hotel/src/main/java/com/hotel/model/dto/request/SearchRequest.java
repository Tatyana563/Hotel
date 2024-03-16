package com.hotel.model.dto.request;

import com.hotel.Utils;
import com.hotel.model.enumeration.RoomType;
import com.hotel.model.enumeration.StarRating;
import com.hotel.validation.date.DateInterval;
import com.hotel.validation.date.ValidInterval;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Data
//@CompareDate(/*before = "checkIn", after="checkOut",*/ message = "The checkIn date must be before  the checkOut date")
@ValidInterval(message = "The interval for SearchRequest is not valid. The checkIn date must be before  the checkOut date")
public class SearchRequest implements DateInterval {

    private StarRating starRating;
    private RoomType roomType;
    private String city;
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Instant checkIn;
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Instant checkOut;

    @Override
    public Instant getFirstDate() {
        return checkIn;
    }

    @Override
    public Instant getLastDate() {
        return checkOut;
    }
}
// Both @CompareDate and @ValidateInterval can be used;