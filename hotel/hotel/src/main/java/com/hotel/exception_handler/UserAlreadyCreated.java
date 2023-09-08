package com.hotel.exception_handler;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public class UserAlreadyCreated extends ResourceNotFoundException {
    public UserAlreadyCreated(String email) {
        super(String.format("The user has already been created with email: %s", email));
    }
}

