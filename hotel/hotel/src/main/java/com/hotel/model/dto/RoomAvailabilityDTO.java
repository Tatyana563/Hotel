package com.hotel.model.dto;

import com.hotel.model.enumeration.RoomType;
import com.hotel.model.enumeration.Sleeps;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class RoomAvailabilityDTO {
    private Integer userId;

    private Instant start;

    private Instant end;

    private int roomId;

    private RoomType type;

    private Sleeps sleeps;

    private Integer price;
}
