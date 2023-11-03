package com.hotel.exception_handler.exception;

import com.hotel.exception_handler.exception.AbstractNotFoundException;

import java.util.UUID;
// TODO : add to handler
public class RegistrationNotFoundException extends AbstractNotFoundException {
    public RegistrationNotFoundException(UUID tokenId) {

        super("Token was not found by id: " , String.valueOf(tokenId));
    }
}

