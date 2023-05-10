package com.hotel.repository;


import com.hotel.model.entity.Hotel;

import lombok.Data;

@Data
public class HotelCounter {
    private Hotel hotel;
    private Long total;

    public HotelCounter(Hotel hotel, Long total) {
        this.hotel = hotel;
        this.total = total;
    }
}
