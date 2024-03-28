package com.hotel.model.dto;

import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.StarRating;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelDTOWithoutCity {
    private Integer id;
    private String name;
    private StarRating starRating;
    private Meals meals;
    private Integer distance;

}
