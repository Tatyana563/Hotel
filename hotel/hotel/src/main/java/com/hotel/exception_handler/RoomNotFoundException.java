package com.hotel.exception_handler;

public class RoomNotFoundException extends Exception {
    public RoomNotFoundException(String message) {
//        super(message);
        super(message, null, true, false);
    }
}

