package com.hotel.controller;

import com.hotel.model.HotelEntity;
import com.hotel.model.Meals;
import com.hotel.model.StarRating;
import com.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HotelController {
    @Autowired
    private HotelService hotelService;

@RequestMapping(value = "/hotel/new", method = RequestMethod.POST)
    public void create(
        @RequestParam("name") String name,
        @RequestParam("rate") StarRating rating,
        @RequestParam("meals") Meals meals,
        @RequestParam("distance") Integer distance
){
    final HotelEntity hotel = new HotelEntity();
    hotel.setName(name);
    hotel.setStarRating(rating);
    hotel.setDistance(distance);
    hotel.setMeals(meals);
    hotelService.save(hotel);
}
}
