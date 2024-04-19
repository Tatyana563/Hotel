package com.hotel.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AverageHotelRatingDto {
    private int hotelId;
    private Double averageMark;
}
