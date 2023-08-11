package com.hotel.exception_handler;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public class HotelNotFoundException extends AbstractNotFoundException {
    public HotelNotFoundException(Integer hotelId) {
        super("Hotel was not found for id: "+ hotelId,hotelId);
    }
}

