package com.hotel.mapper;

import com.hotel.model.dto.RoomDTO;
import com.hotel.model.dto.RoomDTOWithHotelDTO;
import com.hotel.model.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomDTO roomToRoomDTO(Room room);

    @Mapping(target = "roomDTO", source = "room")
    @Mapping(target = "hotelDTO", source = "room.hotel")
    RoomDTOWithHotelDTO roomToRoomDTOWithHotelDTO(Room room);
}
