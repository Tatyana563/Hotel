package com.hotel.model.dto.request;

import com.hotel.model.enumeration.Mark;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class HotelFeedbackRequest {
    //TODO: create validator on field to check that hotel exists by hotelId; the same for user;
//    @NotNull
//    private int userId;
//    @NotNull
//    private int hotelId;
    @NotNull
    private Integer bookRequestId;
    @NotEmpty
    private String comment;
    @NotNull
    private Mark location;
    @NotNull
    private Mark priceQuality;
    @NotNull
    private Mark cleanliness;

}
