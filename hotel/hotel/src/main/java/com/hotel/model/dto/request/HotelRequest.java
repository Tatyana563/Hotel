package com.hotel.model.dto.request;

import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.StarRating;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static com.hotel.exception_handler.exception.ValidationErrorDTO.REQUIRED_FIELD;


@Data
public class HotelRequest {
    @NotEmpty(message = REQUIRED_FIELD)
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
