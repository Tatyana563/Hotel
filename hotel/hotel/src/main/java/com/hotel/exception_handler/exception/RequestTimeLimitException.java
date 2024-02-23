package com.hotel.exception_handler.exception;

public class RequestTimeLimitException extends AbstractNotFoundException {
    public RequestTimeLimitException(String login, long timeToWait) {
        super("Limit in requests was exceeded for login: " + login, login + "Please try in " + timeToWait);
    }
}

