package com.hotel.service.api;

import com.hotel.model.FilterDTO;
import com.hotel.model.dto.HotelBriefInfo;
import com.hotel.model.dto.HotelCounterDTO;
import com.hotel.model.dto.HotelDTO;
import com.hotel.model.dto.HotelDTOWithRooms;
import com.hotel.model.dto.request.HotelRequest;
import com.hotel.model.entity.Hotel;
import com.hotel.model.enumeration.StarRating;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.List;

public interface HotelService {
    HotelDTO save(HotelRequest hotelRequest);

    void delete(int id, Authentication authentication);

    List<HotelBriefInfo> listAllHotelsBriefInfo();

    List<HotelCounterDTO> listHotelsWithAvailableRoomsAccordingToCityAndStarRating(String city, StarRating starRating, Date start, Date end);

    HotelDTOWithRooms hotelWithAvailableRoomsByDates(int hotelId, Date start, Date end);

    List<HotelDTOWithRooms> findHotelsWithFilters(FilterDTO filters);
}
