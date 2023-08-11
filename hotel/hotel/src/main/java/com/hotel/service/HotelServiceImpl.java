package com.hotel.service;

import com.hotel.exception_handler.HotelNotFoundException;
import com.hotel.mapper.HotelMapper;
import com.hotel.model.dto.HotelBriefInfo;
import com.hotel.model.dto.HotelCounterDTO;
import com.hotel.model.dto.HotelDTO;
import com.hotel.model.entity.Hotel;
import com.hotel.repository.HotelCounter;
import com.hotel.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public List<HotelCounterDTO> listHotelsWithAvailableRooms(Date start, Date end) {

        List<HotelCounter> hotelCounters = hotelRepository.listAvailableHotelsByDates(start, end);

        return hotelCounters.stream().map(hotelMapper::hotelCounterToHotelCounterDTO
        ).collect(Collectors.toList());
    }

    @Override
    public HotelDTO hotelWithAvailableRoomsByDates(int hotelId, Date start, Date end) {

        Optional<Hotel> hotel = hotelRepository.hotelWithAvailableRoomsByDates(hotelId, start, end);

        if (!hotel.isPresent()) {
            log.error("Hotel with id '{}'  has not been found in  database", hotelId);
            throw new HotelNotFoundException(hotelId);

        } else return hotelMapper.hotelToHotelDTO(hotel.get());
    }
}
