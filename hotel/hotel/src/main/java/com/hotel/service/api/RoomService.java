package com.hotel.service.api;

import com.hotel.exception_handler.exception.RoomNotFoundException;
import com.hotel.model.FilterDTO;
import com.hotel.model.dto.RoomAvailabilityDTO;
import com.hotel.model.dto.RoomDTO;
import com.hotel.model.dto.RoomDTOWithHotelDTO;
import com.hotel.model.dto.request.BookingRequest;
import com.hotel.model.dto.request.RoomRequest;
import com.hotel.model.dto.response.BookingResponse;
import com.hotel.model.entity.RoomAvailability;

import java.util.List;

public interface RoomService {
    void deleteSeparateRoom(int hotelId, int roomId);

    RoomDTOWithHotelDTO save(int hotelId, RoomRequest roomRequest);

    BookingResponse bookRoom(Integer roomId, BookingRequest bookingRequest) throws RoomNotFoundException;

    List<RoomDTO> findRoomsWithFilters(FilterDTO filters);

    List<RoomAvailabilityDTO> findRoomsBookedByMe(int userId);
}
