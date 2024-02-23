package com.hotel.service.impl;

import com.hotel.exception_handler.exception.CityNotFoundException;
import com.hotel.exception_handler.exception.HotelNotFoundException;
import com.hotel.mapper.HotelMapper;
import com.hotel.model.FilterDTO;
import com.hotel.model.dto.*;
import com.hotel.model.dto.request.HotelRequest;
import com.hotel.model.entity.City;
import com.hotel.model.entity.Hotel;
import com.hotel.model.enumeration.StarRating;
import com.hotel.repository.CityRepository;
import com.hotel.repository.HotelCounter;
import com.hotel.repository.HotelRepository;
import com.hotel.repository.RoomRepository;
import com.hotel.repository.specifications.HotelSpecification;
import com.hotel.service.api.HotelService;
import com.hotel.service.auditable.AuditAnnotation;
import com.hotel.service.auditable.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class
HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final CityRepository cityRepository;
    private final HotelMapper hotelMapper;

    @Transactional
    @Override
    @AuditAnnotation(operation = Operation.HOTEL_CREATE)
    public HotelDTO save(HotelRequest hotelRequest, Authentication authentication) {
        Hotel hotel = hotelMapper.hotelRequestToHotel(hotelRequest);
        ClaimsDto claimsDto = (ClaimsDto) authentication.getPrincipal();
        hotel.setOwnerId(claimsDto.getId());
        Integer cityId = hotelRequest.getCityId();
        City city = cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));
        hotel.setCity(city);
        Hotel savedHotel = hotelRepository.save(hotel);

        return hotelMapper.hotelToHotelDTOWithoutRooms(savedHotel);
    }

    // TODO or else throw HotelNotFound Exc
//    @Transactional
//    @Override
//    public void delete(int id, Authentication authentication) {
//        Optional<Hotel> hotel = findById(id);
//        UserInfoDetails userDetails = (UserInfoDetails) authentication.getPrincipal();
//        if (!hotel.isPresent()) {
//            throw new HotelNotFoundException(id);
//        }
//        if (hotel.get().getUserId() != null && userDetails.getUserId().equals(hotel.get().getUserId().toString())) {
//            hotelRepository.delete(hotel.get());
//        }
//    }
    @Transactional
    @Override
    public void delete(int id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (hotel.isPresent()) {
            hotelRepository.markHotelAsDeleted(id);
            roomRepository.markRoomsAsDeleted(id);
        }
    }

    @Override
    public List<HotelBriefInfo> listAllHotelsBriefInfo(HotelSpecification hotelSpecification) {
        List<Hotel> hotelList = hotelRepository.findAll(hotelSpecification);
        return hotelList.stream().map(hotelMapper::hotelToHotelBriefInfo).collect(Collectors.toList());
    }

    @Override
    public List<HotelBriefInfo> listAllHotelsBriefInfoForOwner(Authentication authentication) {
        ClaimsDto principal = (ClaimsDto) authentication.getPrincipal();
        int userId =  principal.getId();
        return hotelRepository.listHotelsBriefInfoForOwner(userId);
    }

    @Override
    public List<HotelCounterDTO> listHotelsWithAvailableRoomsAccordingToCityAndStarRating(String city, StarRating starRating, Date start, Date end) {
        List<HotelCounter> hotels = hotelRepository.hotelWithAvailableRoomsByDatesAccordingToCityAndStarRating(city, starRating, start, end);
        List<HotelCounterDTO> hotelCounterDTOList = hotels.stream().map(hotelCounter -> hotelMapper.hotelCounterToHotelCounterDTO(hotelCounter)).collect(Collectors.toList());

        return hotelCounterDTOList;

    }

    @Transactional
    public Optional<Hotel> findById(int id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        return hotel;
    }

    @Transactional(readOnly = true)
    public List<Hotel> listAllSorted() {
        return hotelRepository.findByOrderByDistanceAsc();
    }

    @Override
    public HotelDTOWithRooms hotelWithAvailableRoomsByDates(int hotelId, Date start, Date end) {

        Optional<Hotel> hotel = hotelRepository.hotelWithAvailableRoomsByDates(hotelId, start, end);

        if (!hotel.isPresent()) {
            log.error("Hotel with id '{}'  has not been found in  database", hotelId);
            throw new HotelNotFoundException(hotelId);

        } else return hotelMapper.hotelToHotelDTO(hotel.get());
    }


    public List<HotelDTOWithRooms> findHotelsWithFilters(HotelSpecification hotelSpecification) {
        List<Hotel> hotels = hotelRepository.findAll(hotelSpecification);
        return hotels.stream().map(hotelMapper::hotelToHotelDTO).collect(Collectors.toList());
    }
}
