package com.hotel.model.dto.request;

import com.hotel.model.enumeration.RoomType;
import com.hotel.model.enumeration.Sleeps;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class RoomRequest {
    @NotNull
    private Integer number;

    @NotNull
    private RoomType type;

    @NotNull
    private Sleeps sleeps;

    @NotNull
    private Integer price;
    @NotNull
    private Double square;

    @NotNull
    private Boolean parking;

    @NotNull
    private Boolean pets;

}
