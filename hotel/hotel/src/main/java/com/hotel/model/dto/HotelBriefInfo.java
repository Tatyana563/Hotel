package com.hotel.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelBriefInfo {
    private int id;
    private String name;
    private String city;
    private Double averageMark;
}
