package com.hotel.repository;

import com.hotel.model.entity.Hotel;
import com.hotel.model.entity.Room;

import java.util.List;

public interface HotelCounterProjection {
     Hotel getHotel();
     Long getTotal();

}
