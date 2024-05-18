package com.hotel.model.dto.request;

import com.hotel.validation.date.DateInterval;
import com.hotel.validation.date.ValidCustomInterval;
import com.hotel.validation.date.ValidInterval;
import lombok.Data;

import java.time.Instant;

@Data
//@CompareDate(/*before = "checkIn", after="checkOut",*/ message = "The checkIn date must be before  the checkOut date")
@ValidCustomInterval(firstDateFieldName = "checkIn", lastDateFieldName = "checkOut")
public class TestInterval implements DateInterval {
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
