package com.hotel.model.dto;

import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.StarRating;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HotelDTO {
    private Integer id;
    private String name;
    private StarRating starRating;

    private Meals meals;

    private Integer distance;

    private CityDTO city;

    private List<RoomDTO> rooms = new ArrayList<>();
}
