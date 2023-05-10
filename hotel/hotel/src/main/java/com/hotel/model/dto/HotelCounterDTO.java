package com.hotel.model.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HotelCounterDTO {
    private HotelBriefInfo hotel;
    private Long totalNumberOfAvailableRooms;
}
