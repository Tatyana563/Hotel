package com.hotel.service;

import com.hotel.model.dto.HotelBriefInfo;
import com.hotel.model.dto.HotelCounterDTO;
import com.hotel.model.dto.HotelDTO;
import com.hotel.model.entity.Hotel;

import java.util.Date;
import java.util.List;

public interface HotelService {
    void save(Hotel hotel);

    void delete(int id);

    List<HotelBriefInfo> listAllHotelsBriefInfo();

    List<Hotel> listAll();

    List<HotelCounterDTO> listHotelsWithAvailableRooms(Date start, Date end);

    HotelDTO hotelWithAvailableRoomsByDates(int hotelId, Date start, Date end);
}
