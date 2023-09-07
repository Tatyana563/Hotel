package com.hotel.model.dto;

import lombok.Data;

@Data
public class RoomDTOWithHotelDTO {
    private RoomDTO roomDTO;
    private HotelDTO hotelDTO;
}
