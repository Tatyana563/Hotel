package com.hotel.controller;

import com.hotel.exception_handler.RoomNotFoundException;
import com.hotel.model.FilterDTO;
import com.hotel.model.dto.RoomDTO;
import com.hotel.model.dto.request.BookingRequest;
import com.hotel.model.dto.response.BookingResponse;
import com.hotel.service.api.notification.BookingEmailService;
import com.hotel.service.notification.BookingEmailServiceImpl;
import com.hotel.service.api.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(("/rooms"))
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/{roomId}")
    public BookingResponse bookHotelRoom(@ModelAttribute BookingRequest bookingRequest, @PathVariable int roomId) throws RoomNotFoundException {
        return roomService.bookRoom(roomId, bookingRequest);
    }

    @GetMapping("/filter")
    public List<RoomDTO> bookHotelRoomWithFilters(@ModelAttribute FilterDTO filters) throws RoomNotFoundException {
        return roomService.findRoomsWithFilters(filters);
    }
}
