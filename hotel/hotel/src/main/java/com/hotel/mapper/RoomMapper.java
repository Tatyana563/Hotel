package com.hotel.mapper;

import com.hotel.model.dto.RoomDTO;
import com.hotel.model.entity.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomDTO roomToRoomDTO(Room room);
}
