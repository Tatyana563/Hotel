package com.hotel.model;

import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.StarRating;
import lombok.Data;

@Data
public class FilterDTO {
    private Meals meal;
    private Integer distance;
    private Integer price;
    private StarRating starRating;

}
