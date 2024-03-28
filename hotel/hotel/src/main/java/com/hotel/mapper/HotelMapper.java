package com.hotel.mapper;

import com.hotel.model.dto.*;
import com.hotel.model.dto.request.HotelRequest;
import com.hotel.model.entity.Hotel;
import com.hotel.repository.HotelCounter;
import com.hotel.repository.HotelCounterProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoomMapper.class, CityMapper.class})
public interface HotelMapper {


    @Mapping(target = "totalNumberOfAvailableRooms", source = "total")
    HotelCounterDTO hotelCounterToHotelCounterDTO(HotelCounter hotelCounter);

    @Mapping(target = "totalNumberOfAvailableRooms", source = "total")
    HotelCounterDTO hotelCounterToHotelCounterDTO(HotelCounterProjection hotelCounter);

    @Mapping(target = "city", source = "city.name")
    HotelBriefInfo hotelToHotelBriefInfo(Hotel hotel);


    @Mapping(target = "hotelDTO", source = "hotel")
    @Mapping(target = "rooms", source = "roomList")
    HotelDTOWithRooms hotelToHotelDTO(Hotel hotel);

    HotelDTO hotelToHotelDTOWithoutRooms(Hotel hotel);

    Hotel hotelRequestToHotel(HotelRequest hotelRequest);
}
