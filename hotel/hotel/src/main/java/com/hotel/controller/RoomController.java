package com.hotel.controller;

import com.hotel.exception_handler.exception.RoomNotFoundException;
import com.hotel.model.FilterDTO;
import com.hotel.model.dto.ClaimsDto;
import com.hotel.model.dto.RoomAvailabilityDTO;
import com.hotel.model.dto.RoomDTO;
import com.hotel.model.dto.request.BookingRequest;
import com.hotel.model.dto.response.BookingResponse;
import com.hotel.service.api.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(("/rooms"))
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/{roomId}")
    public BookingResponse bookHotelRoom(@RequestBody  BookingRequest bookingRequest, @PathVariable int roomId) throws RoomNotFoundException {
        return roomService.bookRoom(roomId, bookingRequest);
    }

    @GetMapping("/filter")
    public List<RoomDTO> bookHotelRoomWithFilters(@ModelAttribute FilterDTO filters) throws RoomNotFoundException {
        return roomService.findRoomsWithFilters(filters);
    }
    @GetMapping("/booked_by_me")
    public List<RoomAvailabilityDTO> findRomsBookedByMe(Authentication authentication) throws RoomNotFoundException {
        ClaimsDto claimsDto = (ClaimsDto) authentication.getPrincipal();
        int userId = claimsDto.getId();
        return roomService.findRoomsBookedByMe(userId);
    }

}
