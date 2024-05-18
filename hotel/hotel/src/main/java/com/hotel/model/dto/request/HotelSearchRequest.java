package com.hotel.model.dto.request;

import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.StarRating;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class HotelSearchRequest {

    private Meals meal;
    @Positive
    private Integer distance;
    @Positive
    private Integer price;

    private StarRating starRating;
}
