package com.hotel.mapper;

import com.hotel.model.dto.HotelCounterDTO;
import com.hotel.repository.HotelCounter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mapping(target = "hotel", source = "hotelCounter.hotel")
    @Mapping(target = "totalNumberOfAvailableRooms", source = "hotelCounter.total")
    HotelCounterDTO hotelCounterToHotelCounterDTO(HotelCounter hotelCounter);
}
