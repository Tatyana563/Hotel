package com.hotel.controller;

import com.hotel.model.entity.Hotel;
import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.StarRating;
import com.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Secured("ADMIN_ROLE")
@RequestMapping(value = "/hotel2/new", method = RequestMethod.POST)
    public String create(
        @RequestParam("name") String name,
        @RequestParam("rate") String rating,
        @RequestParam(value = "meals", required = false) String meals,
        @RequestParam("distance") Integer distance
){
    final Hotel hotel = new Hotel();
    hotel.setName(name);
    hotel.setStarRating(StarRating.valueOf(rating));
    hotel.setDistance(distance);
    hotel.setMeals(Meals.valueOf(meals));
    hotelService.save(hotel);
return "hotel_creation";
}

@GetMapping("/")
    public String mainPage(final Model model) {
    model.addAttribute("hotels", hotelService.listAll());
    return "/all_hotels";

}

}
