package com.hotel.exception_handler.exception;

public class UserNotFoundException extends AbstractNotFoundException {
    public UserNotFoundException(String email) {
        super("User was not found for email: "+ email, String.valueOf(email)+"Please register http://{{host}}/registration");
    }
}

