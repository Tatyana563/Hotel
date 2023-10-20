package com.hotel.model.dto.request;

import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.StarRating;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class HotelRequest {
    @NotEmpty
    private String name;
    @NotNull
    private StarRating starRating;
    @NotNull
    private Meals meals;
    @NotNull
    private Integer distance;
    @NotNull
    private Integer cityId;
}
