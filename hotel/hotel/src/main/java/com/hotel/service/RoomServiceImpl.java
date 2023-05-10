package com.hotel.service;

import com.hotel.mapper.HotelMapper;
import com.hotel.model.dto.HotelCounterDTO;
import com.hotel.model.entity.Room;
import com.hotel.repository.HotelCounter;
import com.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private static final HotelMapper hotelMapper = Mappers.getMapper(HotelMapper.class);

    @Override
    public void save(Room room) {
        roomRepository.save(room);
    }

    public List<HotelCounterDTO> listHotelsWithAvailableRooms(Date start, Date end) {

        List<HotelCounter> hotelCounters = roomRepository.listAvailableRoomsByDates(start, end);

        return hotelCounters.stream().map(hotelCounter -> {
            HotelCounterDTO hotelCounterDTO = hotelMapper.hotelCounterToHotelCounterDTO(hotelCounter);
            return hotelCounterDTO;
        }).collect(Collectors.toList());
    }
}
