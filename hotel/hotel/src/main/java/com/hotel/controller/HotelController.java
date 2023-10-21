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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/hotel")
@Validated
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/all_hotels_brief")
    public Collection<HotelBriefInfo> allHotelsBriefInfo() {
        return hotelService.listAllHotelsBriefInfo();
    }


    @PostMapping("/add")
    // TODO: move into service, use mapper!!!!
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Hotel> create(@RequestBody @Valid HotelRequest hotelRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //TODO: return hotelDTO
        return ResponseEntity.ok(hotelService.save(hotelRequest));

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


