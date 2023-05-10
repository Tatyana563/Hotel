package com.hotel.controller;

import com.hotel.model.entity.Hotel;
import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.StarRating;
import com.hotel.repository.HotelRepository;
import com.hotel.service.HotelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
public class HotelController {
    @Autowired
    private HotelServiceImpl hotelService;
    @Autowired
    private HotelRepository hotelRepository;

    // @Secured("ADMIN_ROLE")
    @RequestMapping(value = "/hotel2/new", method = RequestMethod.POST)
    public String create(
            @RequestParam("name") String name,
            @RequestParam("rate") String rating,
            @RequestParam(value = "meals", required = false) String meals,
            @RequestParam("distance") Integer distance
    ) {
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
        // model.addAttribute("hotels", hotelService.listAll());
        model.addAttribute("hotels", hotelService.listAllSorted());
        return "all_hotels";

    }

    @PostMapping("/edit")
    public String changeHotel(Model model,
                              @RequestParam(name = "hotelId") final int hotelId) {
        Optional<Hotel> hotel = hotelService.findById(hotelId);
        if (hotel.isPresent()) {
            model.addAttribute("hotel", hotel.get());
        }
        hotelService.delete(hotelId);
        // hotelService.save(hotel.get());
        return "hotel_edit";

    }

    @RequestMapping(value = "/hotel2/edit", method = RequestMethod.POST)
    public String edit(
            @RequestParam("name") String name,
            @RequestParam("rate") String rating,
            @RequestParam(value = "meals", required = false) String meals,
            @RequestParam("distance") Integer distance
    ) {
        final Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setStarRating(StarRating.valueOf(rating));
        hotel.setDistance(distance);
        hotel.setMeals(Meals.valueOf(meals));
        hotelService.save(hotel);
        return "hotel_creation";
    }

    // @Secured("ADMIN_ROLE")
    @GetMapping("/new")
    public String newPage(final Model model) {
        // model.addAttribute("hotels", hotelService.listAll());
        return "hotel_creation";

    }
  /*  @GetMapping("/edit")
    public String newPageEdit(final Model model) {
        // model.addAttribute("hotels", hotelService.listAll());
        return "hotel_creation";

    }*/
/*    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Hotel> accomodation;

        if (filter != null && !filter.isEmpty()) {
            accomodation= hotelRepository.findByName(filter);
        } else {
            accomodation = hotelRepository.findAll();
        }

        model.put("accomodation",accomodation );

        return "all_hotels";
    }*/

}
