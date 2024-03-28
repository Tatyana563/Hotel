package com.hotel.service.api;

import com.hotel.model.FilterDTO;
import com.hotel.model.dto.*;
import com.hotel.model.dto.request.HotelRequest;
import com.hotel.model.enumeration.StarRating;
import com.hotel.repository.specifications.HotelSpecification;
import org.springframework.security.core.Authentication;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public interface HotelService {
    HotelDTO save(HotelRequest hotelRequest, Authentication authentication);

    void delete(int id);

    List<HotelBriefInfo> listAllHotelsBriefInfo(HotelSpecification hotelSpecification);
    List<HotelBriefInfo> listAllHotelsBriefInfoForOwner(int userId);
    List<HotelCounterDTO> listHotelsWithAvailableRoomsAccordingToCityAndStarRating(String city, StarRating starRating, Instant start, Instant end);

    HotelDTOWithRooms hotelWithAvailableRoomsByDates(int hotelId, Instant start, Instant end);

    List<HotelDTOWithRooms> findHotelsWithFilters(HotelSpecification hotelSpecification);

    <T> List<T> findAll( Class<T> type);
}
