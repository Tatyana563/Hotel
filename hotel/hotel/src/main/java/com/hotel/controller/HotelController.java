package com.hotel.controller;

import com.hotel.model.FilterDTO;
import com.hotel.model.dto.HotelBriefInfo;
import com.hotel.model.dto.HotelCounterDTO;
import com.hotel.model.dto.HotelDTOWithRooms;
import com.hotel.model.dto.request.HotelRequest;
import com.hotel.model.dto.request.SearchRequest;
import com.hotel.model.dto.request.SearchRequestDates;
import com.hotel.model.entity.Hotel;
import com.hotel.service.api.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/hotel")
@Validated
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping("/all_hotels_brief")
    public Collection<HotelBriefInfo> allHotelsBriefInfo() {
        return hotelService.listAllHotelsBriefInfo();
    }


    @PostMapping("/add")
    // TODO: move into service, use mapper!!!!
    public ResponseEntity<String> create(@RequestBody @Valid HotelRequest hotelRequest) {
        try {
            Hotel hotel = hotelService.save(hotelRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Hotel created successfully with id:" + hotel.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hotel was not created");
        }
    }

    @GetMapping("/delete/{deleteId}")
    public void deleteById(@PathVariable("deleteId") int id) {
        hotelService.delete(id);
    }

    @GetMapping("/search")
    public List<HotelCounterDTO> searchAvailableHotels(@ModelAttribute SearchRequest searchRequest) {
        return hotelService.listHotelsWithAvailableRoomsAccordingToCityAndStarRating(searchRequest.getCity(), searchRequest.getStarRating(), searchRequest.getCheckIn(), searchRequest.getCheckOut());
    }

    @GetMapping("/{hotelId}/filter")
    public HotelDTOWithRooms filterHotelRooms(@ModelAttribute @Valid SearchRequestDates searchRequestDates,/* @Valid @HotelId*/ @PathVariable int hotelId) {
        return hotelService.hotelWithAvailableRoomsByDates(hotelId, searchRequestDates.getCheckIn(), searchRequestDates.getCheckOut());
    }

    @GetMapping("/filter")
    public List<HotelDTOWithRooms> getHotelsWithFilters(@ModelAttribute FilterDTO filters) {
        return hotelService.findHotelsWithFilters(filters);

    }
}


