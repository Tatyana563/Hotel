package com.hotel.mapper;

import com.hotel.model.dto.RoomAvailabilityDTO;
import com.hotel.model.entity.BookRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomAvailabilityMapper {
    @Mapping(target = "type", source = "room.type")
    @Mapping(target = "sleeps", source = "room.sleeps")
    @Mapping(target = "price", source = "room.price")
    RoomAvailabilityDTO roomAvailabilityToRoomAvailabilityDTO(BookRequest roomAvailability);
}
