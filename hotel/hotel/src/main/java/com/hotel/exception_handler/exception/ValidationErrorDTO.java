package com.hotel.exception_handler.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationErrorDTO {
    private static final String ENUM_HOTEL_MEAL = "data-validation.hotel-meal-invalid";
    private static final String HOTEL_NOT_FOUND = "The hotel was not found in DB";
    public static final String REQUIRED_FIELD = "data-validation.is-required";
    private String fieldName;
    private Object rejectedValue;
    private List<String> acceptableValues;
    private String message;
}
