package com.hotel.exception_handler.exception;

public class RoomNotFoundException extends AbstractNotFoundException {
    public RoomNotFoundException(Integer roomId) {
        super("Room", String.valueOf(roomId));
    }
}

