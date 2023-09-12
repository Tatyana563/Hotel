package com.hotel.model.dto;

import com.hotel.model.enumeration.RoomType;
import com.hotel.model.enumeration.Sleeps;
import lombok.Data;

@Data
public class RoomDTO {

    private Integer id;

    private RoomType type;

    private Sleeps sleeps;

    private Integer price;

    private Double square;

    private Boolean parking;

    private Boolean pets;
    private HotelDTO hotel;
}
