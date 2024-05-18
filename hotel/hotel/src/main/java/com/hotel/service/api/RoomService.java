package com.hotel.service.api;

import com.hotel.exception_handler.exception.RoomNotFoundException;
import com.hotel.model.dto.RoomAvailabilityDTO;
import com.hotel.model.dto.RoomDTOWithHotelDTO;
import com.hotel.model.dto.request.BookingRequest;
import com.hotel.model.dto.request.RoomRequest;
import com.hotel.model.dto.response.BookingResponse;
import com.hotel.model.entity.Room;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface RoomService {
    void deleteSeparateRoom(int hotelId, int roomId);

    RoomDTOWithHotelDTO save(int hotelId, RoomRequest roomRequest);

    BookingResponse bookRoom(Integer roomId, BookingRequest bookingRequest) throws RoomNotFoundException;

//    List<RoomDTO> findRoomsWithFilters(FilterDTO filters);

    List<RoomDTOWithHotelDTO> findRoomsWithFilters(Specification<Room> filters);

    List<RoomAvailabilityDTO> findRoomsBookedByMe(int userId);
}
