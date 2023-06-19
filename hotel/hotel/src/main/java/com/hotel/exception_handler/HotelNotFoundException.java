package com.hotel.exception_handler;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public class HotelNotFoundException extends ResourceNotFoundException {
    public HotelNotFoundException(String message) {
        super(message);
    }
}

