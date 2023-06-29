package com.hotel.controller;

import com.hotel.exception_handler.RoomNotFoundException;
import com.hotel.model.dto.request.BookingRequest;
import com.hotel.model.dto.response.BookingResponse;
import com.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(("/rooms"))
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/{roomId}")
    public ResponseEntity<BookingResponse> bookHotelRoom(@ModelAttribute BookingRequest bookingRequest, @PathVariable int roomId) throws RoomNotFoundException {
        BookingResponse bookingResponse = roomService.bookRoom(roomId, bookingRequest.getHotelId(), bookingRequest.getCheckIn(), bookingRequest.getCheckOut());

        return ResponseEntity.status(HttpStatus.CREATED).body(bookingResponse);
    }
}
