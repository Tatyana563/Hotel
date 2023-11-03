package com.hotel.exception_handler.exception;

import com.hotel.exception_handler.exception.AbstractNotFoundException;

public class HotelNotFoundException extends AbstractNotFoundException {
    public HotelNotFoundException(Integer hotelId) {
        super("Hotel was not found for id: "+ hotelId, String.valueOf(hotelId));
    }
}

