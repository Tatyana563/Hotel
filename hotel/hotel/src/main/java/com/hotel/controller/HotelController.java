package com.hotel.controller;

import com.hotel.model.dto.HotelBriefInfo;
import com.hotel.model.dto.HotelCounterDTO;
import com.hotel.model.dto.HotelDTOWithRooms;
import com.hotel.model.dto.request.SearchRequest;
import com.hotel.model.dto.request.SearchRequestDates;
import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.StarRating;
import com.hotel.repository.specifications.HotelSpecification;
import com.hotel.service.api.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('USER')")
    public Collection<HotelBriefInfo> allHotelsBriefInfo(@RequestParam(required = false) Meals meal,
                                                         @RequestParam(required = false) Integer distance,
                                                         @RequestParam(required = false) Integer price,
                                                         @RequestParam(required = false) StarRating starRating) {
        HotelSpecification hotelSpecification = new HotelSpecification.HotelSpecificationBuilder()
                .meal(meal)
                .distance(distance)
                .price(price)
                .starRating(starRating)
                .build();
        return hotelService.listAllHotelsBriefInfo(hotelSpecification);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER')")
    public List<HotelCounterDTO> searchAvailableHotels(@ModelAttribute SearchRequest searchRequest) {
        return hotelService.listHotelsWithAvailableRoomsAccordingToCityAndStarRating(searchRequest.getCity(), searchRequest.getStarRating(), searchRequest.getCheckIn(), searchRequest.getCheckOut());
    }

    @GetMapping("/{hotelId}/filter")
    @PreAuthorize("hasRole('USER')")
    public HotelDTOWithRooms filterHotelRooms(@ModelAttribute @Valid SearchRequestDates searchRequestDates,/* @Valid @HotelId*/ @PathVariable int hotelId) {
        return hotelService.hotelWithAvailableRoomsByDates(hotelId, searchRequestDates.getCheckIn(), searchRequestDates.getCheckOut());
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('USER')")
    public List<HotelDTOWithRooms> getHotelsWithFilters(@RequestParam(required = false) Meals meal,
                                                        @RequestParam(required = false) Integer distance,
                                                        @RequestParam(required = false) Integer price,
                                                        @RequestParam(required = false) StarRating starRating) {
        HotelSpecification hotelSpecification = new HotelSpecification.HotelSpecificationBuilder()
                .meal(meal)
                .distance(distance)
                .price(price)
                .starRating(starRating)
                .build();
        return hotelService.findHotelsWithFilters(hotelSpecification);

    }
}


