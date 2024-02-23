package com.hotel.exception_handler.exception;

public class RoomNotFoundException extends AbstractNotFoundException {
    public RoomNotFoundException(Integer roomId) {

        super("Room was not found with roomId: " + roomId, String.valueOf(roomId));
    }
}

