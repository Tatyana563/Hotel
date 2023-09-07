package com.hotel.controller;

import com.hotel.model.FilterDTO;
import com.hotel.model.dto.HotelBriefInfo;
import com.hotel.model.dto.HotelCounterDTO;
import com.hotel.model.dto.HotelDTOWithRooms;
import com.hotel.model.dto.request.SearchRequest;
import com.hotel.model.dto.request.SearchRequestDates;
import com.hotel.model.entity.Hotel;
import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.StarRating;
import com.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/hotel")
@Validated
public class HotelRestController {
    @Autowired
    private HotelService hotelService;

    @GetMapping("/all_hotels_brief")
    public Collection<HotelBriefInfo> allHotelsBriefInfo() {
        return hotelService.listAllHotelsBriefInfo();
    }

    //http://localhost:8080/hotel/add?name=Barcelona=&star=FIVE&meal=BREAKFAST&distance=500&cityId=1
    @GetMapping("/add")
    public Hotel create(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "star", required = false) String starRating,
            @RequestParam(value = "meal", required = false) String meal,
            @RequestParam(value = "distance", required = false) Integer distance,
            @RequestParam(value = "cityId", required = false) Integer cityId

    ) {
        final Hotel hotel = new Hotel();
        hotel.setCityId(cityId);
        hotel.setMeals(Meals.valueOf(meal));
        hotel.setDistance(distance);
        hotel.setStarRating(StarRating.valueOf(starRating));
        hotel.setName(name);
        // hotelService.save(hotel);
        return hotel;
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
    public List<HotelDTOWithRooms>getHotelsWithFilters(@ModelAttribute FilterDTO filters) {
       return  hotelService.findHotelsWithFilters(filters);

        }
    }


