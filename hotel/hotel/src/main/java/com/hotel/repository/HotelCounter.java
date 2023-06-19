package com.hotel.repository;


import com.hotel.model.entity.Hotel;
import com.hotel.model.entity.Room;
import lombok.Data;

import java.util.List;

@Data
public class HotelCounter {
    private Hotel hotel;
    private Long total;
    private List<Room> availableRooms;

    public HotelCounter(Hotel hotel, Long total) {
        this.hotel = hotel;
        this.total = total;
    }
}
