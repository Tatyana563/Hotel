package com.hotel.service;

import com.hotel.exception_handler.RoomNotFoundException;
import com.hotel.model.entity.Room;

import java.util.Date;

public interface RoomService {
    void save(Room room);

    void bookRoom(Integer roomId, Date start, Date end) throws RoomNotFoundException;

}
