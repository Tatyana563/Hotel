package com.hotel.service;

import com.hotel.model.entity.Hotel;

public interface HotelService {
    void save(Hotel hotel);
    void delete (int id);
}
