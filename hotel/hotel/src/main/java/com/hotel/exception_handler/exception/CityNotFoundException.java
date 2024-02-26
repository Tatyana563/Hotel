package com.hotel.exception_handler.exception;

public class CityNotFoundException extends AbstractNotFoundException {
    public CityNotFoundException(Integer cityId) {
        super("City", String.valueOf(cityId));
    }
}

