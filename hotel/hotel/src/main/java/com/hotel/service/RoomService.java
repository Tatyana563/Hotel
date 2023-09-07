package com.hotel.service;

import com.hotel.exception_handler.RoomNotFoundException;
import com.hotel.model.FilterDTO;
import com.hotel.model.dto.RoomDTOWithHotelDTO;
import com.hotel.model.dto.request.BookingRequest;
import com.hotel.model.dto.response.BookingResponse;
import com.hotel.model.entity.Room;

import java.util.List;

public interface RoomService {
    void save(Room room);

    BookingResponse bookRoom(Integer roomId, BookingRequest bookingRequest) throws RoomNotFoundException;

    List<RoomDTOWithHotelDTO> findRoomsWithFilters(FilterDTO filters);

}
