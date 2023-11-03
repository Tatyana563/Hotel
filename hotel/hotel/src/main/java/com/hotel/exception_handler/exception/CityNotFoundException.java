package com.hotel.exception_handler.exception;

import com.hotel.exception_handler.exception.AbstractNotFoundException;

public class CityNotFoundException extends AbstractNotFoundException {
    public CityNotFoundException(Integer cityId) {
        super("City was not found for id: "+ cityId, String.valueOf(cityId));
    }
}

