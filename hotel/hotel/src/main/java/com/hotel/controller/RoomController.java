package com.hotel.controller;

import com.hotel.model.dto.HotelCounterDTO;
import com.hotel.model.dto.SearchRequest;
import com.hotel.service.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(("/rooms"))
public class RoomController {
    private final RoomServiceImpl roomService;

    @GetMapping
    public List<HotelCounterDTO> roomBooking(@ModelAttribute SearchRequest searchRequest) {
        return roomService.listHotelsWithAvailableRooms(searchRequest.getCheckIn(), searchRequest.getCheckOut());
    }
}
