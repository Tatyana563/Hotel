package com.hotel.exception_handler.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super(String.format("User was not found with email: %s", email));
    }
}

