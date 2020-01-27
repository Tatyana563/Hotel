package com.hotel.service;

import com.hotel.model.HotelEntity;

public interface HotelService {
    void save(HotelEntity hotelEntity);
    void delete (int id);
}
