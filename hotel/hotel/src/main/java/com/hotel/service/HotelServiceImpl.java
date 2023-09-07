package com.hotel.service;

import com.hotel.exception_handler.HotelNotFoundException;
import com.hotel.mapper.HotelMapper;
import com.hotel.model.FilterDTO;
import com.hotel.model.dto.HotelBriefInfo;
import com.hotel.model.dto.HotelCounterDTO;
import com.hotel.model.dto.HotelDTOWithRooms;
import com.hotel.model.entity.Hotel;
import com.hotel.model.enumeration.StarRating;
import com.hotel.repository.HotelCounter;
import com.hotel.repository.HotelRepository;
import com.hotel.repository.specifications.HotelSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    private final HotelMapper hotelMapper;

    @Transactional
    @Override
    public void save(Hotel hotel) {
        hotelRepository.save(hotel);

    }
// TODO or else throw HotelNotFound Exc
    @Transactional
    @Override
    public void delete(int id) {
        Hotel hotel = findById(id).get();
        hotelRepository.delete(hotel);
    }

    @Override
    public List<HotelBriefInfo> listAllHotelsBriefInfo() {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Hotel> listAll() {
        return hotelRepository.findAll();
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
        return Optional.ofNullable(hotel).orElse(null);
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


    public List<HotelDTOWithRooms> findHotelsWithFilters(FilterDTO filters) {
        Specification<Hotel> spec = new HotelSpecification(filters);
        List<Hotel> hotels = hotelRepository.findAll(spec);
        return hotels.stream().map(hotelMapper::hotelToHotelDTO).collect(Collectors.toList());
    }
}
