package com.hotel.exception_handler.exception;


public class RegistrationNotFoundException extends AbstractNotFoundException {

    public RegistrationNotFoundException(String message, String resourceId) {
        super(message, resourceId);
    }
}

