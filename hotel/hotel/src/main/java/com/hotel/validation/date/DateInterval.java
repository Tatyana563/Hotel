package com.hotel.validation.date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public interface DateInterval {
    @JsonIgnore
    Instant getFirstDate();
    @JsonIgnore
    Instant getLastDate();
}
// different DTOs have different names for data fields