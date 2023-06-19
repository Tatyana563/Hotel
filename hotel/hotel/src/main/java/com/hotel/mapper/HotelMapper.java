package com.hotel.mapper;

import com.hotel.model.dto.HotelBriefInfo;
import com.hotel.model.dto.HotelCounterDTO;
import com.hotel.model.dto.HotelDTO;
import com.hotel.model.entity.Hotel;
import com.hotel.repository.HotelCounter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoomMapper.class, CityMapper.class})
public interface HotelMapper {


    @Mapping(target = "totalNumberOfAvailableRooms", source = "total")
    HotelCounterDTO hotelCounterToHotelCounterDTO(HotelCounter hotelCounter);

    @Mapping(target = "city", source = "city.name")
    HotelBriefInfo hotelToHotelBriefInfo(Hotel hotel);

    @Mapping(target = "rooms", source = "roomList")
    HotelDTO hotelToHotelDTO(Hotel hotel);
}
