package com.hotel.service.api;

import com.hotel.model.FilterDTO;
import com.hotel.model.dto.HotelBriefInfo;
import com.hotel.model.dto.HotelCounterDTO;
import com.hotel.model.dto.HotelDTOWithRooms;
import com.hotel.model.dto.request.HotelRequest;
import com.hotel.model.entity.Hotel;
import com.hotel.model.enumeration.StarRating;

import java.util.Date;
import java.util.List;

public interface HotelService {
    Hotel save(HotelRequest hotelRequest);

    void delete(int id);

    List<HotelBriefInfo> listAllHotelsBriefInfo();

    List<HotelCounterDTO> listHotelsWithAvailableRoomsAccordingToCityAndStarRating(String city, StarRating starRating, Date start, Date end);

    HotelDTOWithRooms hotelWithAvailableRoomsByDates(int hotelId, Date start, Date end);

    List<HotelDTOWithRooms> findHotelsWithFilters(FilterDTO filters);
}
