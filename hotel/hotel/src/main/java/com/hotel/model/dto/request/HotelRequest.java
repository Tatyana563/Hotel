package com.hotel.model.dto.request;

import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.StarRating;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class HotelRequest {

    private String name;

    private StarRating starRating;

    private Meals meals;
    private Integer distance;
    private Integer cityId;
}
