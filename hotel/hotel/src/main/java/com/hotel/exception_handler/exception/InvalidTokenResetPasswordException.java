package com.hotel.exception_handler.exception;

public class InvalidTokenResetPasswordException extends AbstractNotFoundException {
    public InvalidTokenResetPasswordException() {
        super("Token is invalid or expired");
    }
}

