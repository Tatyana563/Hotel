package com.hotel.exception_handler.exception;

public class InvalidDataFormatException extends RuntimeException{
    public InvalidDataFormatException(String message, Throwable cause) {
        super("The date format of %s is invalid", cause);
    }
}
