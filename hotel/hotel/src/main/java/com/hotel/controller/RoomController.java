package com.hotel.controller;

import com.hotel.exception_handler.RoomNotFoundException;
import com.hotel.model.dto.request.BookingRequest;
import com.hotel.model.dto.response.BookingResponse;
import com.hotel.model.dto.response.RequestStatus;
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
        BookingResponse bookingResponse = roomService.bookRoom(roomId, bookingRequest.getCheckIn(), bookingRequest.getCheckOut());
        HttpStatus httpStatus = bookingResponse.getStatus().equals(RequestStatus.SUCCESSFULLY_BOOKED)
                ? HttpStatus.CREATED
                : HttpStatus.OK;
        return ResponseEntity.status(httpStatus).body(bookingResponse);
    }

}