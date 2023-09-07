package com.hotel.mapper;

import com.hotel.model.dto.RoomDTO;
import com.hotel.model.dto.RoomDTOWithHotelDTO;
import com.hotel.model.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomDTO roomToRoomDTO(Room room);

    @Mapping(target = "hotelDTO.id", source = "hotel.id")
    @Mapping(target = "hotelDTO.name", source = "hotel.name")
    @Mapping(target = "hotelDTO.starRating", source = "hotel.starRating")
    @Mapping(target = "hotelDTO.meals", source = "hotel.meals")
    @Mapping(target = "hotelDTO.distance", source = "hotel.distance")
    @Mapping(target = "hotelDTO.city.name", source = "hotel.city.name")
    @Mapping(target = "hotelDTO.city.country", source = "hotel.city.country")
    @Mapping(target = "roomDTO.id", source = "id")
    @Mapping(target = "roomDTO.type", source = "type")
    @Mapping(target = "roomDTO.sleeps", source = "sleeps")
    @Mapping(target = "roomDTO.price", source = "price")
    @Mapping(target = "roomDTO.square", source = "square")
    @Mapping(target = "roomDTO.parking", source = "parking")
    @Mapping(target = "roomDTO.pets", source = "pets")
    RoomDTOWithHotelDTO roomToRoomDTOWithHotelDTO(Room room);
}
