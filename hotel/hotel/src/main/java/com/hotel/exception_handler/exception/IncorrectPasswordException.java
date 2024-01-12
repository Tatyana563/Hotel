package com.hotel.exception_handler.exception;

public class IncorrectPasswordException extends AbstractNotFoundException {
    public IncorrectPasswordException() {
        super("Login or password is not correct");
    }
}

