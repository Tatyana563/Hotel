package com.hotel.service;

import com.hotel.exception_handler.RoomNotFoundException;
import com.hotel.model.dto.request.BookingRequest;
import com.hotel.model.dto.response.BookingResponse;
import com.hotel.model.entity.Room;

public interface RoomService {
    void save(Room room);

    BookingResponse bookRoom(Integer roomId, BookingRequest bookingRequest) throws RoomNotFoundException;

}
