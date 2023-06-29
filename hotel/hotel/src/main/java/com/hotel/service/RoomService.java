package com.hotel.service;

import com.hotel.exception_handler.RoomNotFoundException;
import com.hotel.model.dto.response.BookingResponse;
import com.hotel.model.entity.Room;

import java.util.Date;

public interface RoomService {
    void save(Room room);

    BookingResponse bookRoom(Integer roomId, Integer hotelId, Date start, Date end) throws RoomNotFoundException;

}
