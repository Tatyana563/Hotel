package com.hotel.model.dto.request;

import com.hotel.model.enumeration.RoomType;
import com.hotel.model.enumeration.StarRating;
import com.hotel.validation.date.DateInterval;
import com.hotel.validation.date.ValidateInterval;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
//@CompareDate(/*before = "checkIn", after="checkOut",*/ message = "The checkIn date must be before  the checkOut date")
@ValidateInterval(message = "The interval for SearchRequest is not valid. The checkIn date must be before  the checkOut date")
public class SearchRequest implements DateInterval {

    private StarRating starRating;
    private RoomType roomType;
    private String city;
    @DateTimeFormat(pattern = "dd_MM_yyyy")
    private Date checkIn;
    @DateTimeFormat(pattern = "dd_MM_yyyy")
    private Date checkOut;

    @Override
    public Date getFirstDate() {
        return checkIn;
    }

    @Override
    public Date getLastDate() {
        return checkOut;
    }
}
// Both @CompareDate and @ValidateInterval can be used;