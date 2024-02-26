package com.hotel.exception_handler.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String token) {
        super(String.format("Token is invalid or expired: %s", token));
    }
}

