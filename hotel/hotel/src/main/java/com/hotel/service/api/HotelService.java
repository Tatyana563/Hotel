package com.hotel.service.api;

import com.hotel.model.dto.HotelBriefInfo;
import com.hotel.model.dto.HotelCounterDTO;
import com.hotel.model.dto.HotelDTO;
import com.hotel.model.dto.HotelDTOWithRooms;
import com.hotel.model.dto.request.HotelRequest;
import com.hotel.model.entity.Hotel;
import com.hotel.model.enumeration.StarRating;
import com.hotel.repository.specifications.HotelSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;

import java.time.Instant;
import java.util.List;

public interface HotelService {
    HotelDTO save(HotelRequest hotelRequest, Authentication authentication);

    void delete(int id);

    List<HotelBriefInfo> listAllHotelsBriefInfo(Specification<Hotel> hotelSpecification);

    List<HotelCounterDTO> listHotelsWithAvailableRoomsAccordingToCityAndStarRating(String city, StarRating starRating, Instant start, Instant end);

    HotelDTOWithRooms hotelWithAvailableRoomsByDates(int hotelId, Instant start, Instant end);

    List<HotelDTOWithRooms> findHotelsWithFilters(HotelSpecification hotelSpecification);

    <T> List<T> findAll(Class<T> type);
}
