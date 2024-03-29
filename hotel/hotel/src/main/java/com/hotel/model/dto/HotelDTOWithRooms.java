package com.hotel.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HotelDTOWithRooms {
    private HotelDTO hotelDTO;

    private List<RoomDTO> rooms = new ArrayList<>();
}
