package com.hotel.controller;

import com.hotel.model.entity.Hotel;
import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.StarRating;
import com.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotel")
public class HotelRestController {
    @Autowired
    private HotelService hotelService;

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
    public void deleteById(@PathVariable("deletId") int id) {
        hotelService.delete(id);
    }
}
/*
  @PostMapping("/updateHotelRates")
    public HotelEntity updateHotelrates()

}
*/
