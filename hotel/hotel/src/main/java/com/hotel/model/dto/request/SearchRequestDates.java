package com.hotel.model.dto.request;

import com.hotel.Utils;
import com.hotel.validation.date.DateInterval;
import com.hotel.validation.date.ValidInterval;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.Date;

@Data
//@CompareDate(/*before = "checkIn", after="checkOut",*/ message = "The checkIn date must be before  the checkOut date")
@ValidInterval(message = "The interval for SearchRequest is not valid. The checkIn date must be before  the checkOut date")
public class SearchRequestDates implements DateInterval {
 //   @DateTimeFormat(pattern = "dd_MM_yyyy")
    private Instant checkIn;
//    @DateTimeFormat(pattern = "dd_MM_yyyy")
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
