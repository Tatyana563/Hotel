package com.hotel.validation.date;

import java.time.Instant;
import java.util.Date;

public interface DateInterval {
    Instant getFirstDate();

    Instant getLastDate();
}
// different DTOs have different names for data fields