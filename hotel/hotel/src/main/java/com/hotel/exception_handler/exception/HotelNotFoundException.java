package com.hotel.exception_handler.exception;

public class HotelNotFoundException extends AbstractNotFoundException {
    public HotelNotFoundException(Integer hotelId) {
        super("Hotel", String.valueOf(hotelId));
    }
}

