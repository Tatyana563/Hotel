package com.hotel.exception_handler.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String role) {
        super(String.format("The following role doesn't exist: %s", role));
    }
}

