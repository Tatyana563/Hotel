package com.hotel.exception_handler.exception;

public class BookingRequestNotFoundException extends RuntimeException {
    public BookingRequestNotFoundException(int id) {
        super(String.format("The following booking request doesn't exist: %s", id));
    }
}

