package com.hotel.model.dto.response;

public enum ErrorTypeEnum {
    VALIDATION_ERROR, // create hotel name required, negative instead of positive
    DESERIALIZATION_ERROR, //create hotel, starRating SEVEN, create hotel "distance": {"value":2.5},
    COMMON_ERROR
}
