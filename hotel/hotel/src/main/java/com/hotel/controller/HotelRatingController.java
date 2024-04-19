package com.hotel.controller;

import com.hotel.model.dto.ClaimsDto;
import com.hotel.model.dto.request.HotelFeedbackRequest;
import com.hotel.service.impl.RatingServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
@Validated
@RequiredArgsConstructor
public class HotelRatingController {

    private final RatingServiceImpl ratingService;

    @PostMapping("/comment/add")
    @PreAuthorize("hasRole('USER')")
    public void addComment(@RequestBody @Valid HotelFeedbackRequest hotelRatingRequest, Authentication authentication) throws IllegalAccessException {
        ClaimsDto claimsDto = (ClaimsDto) authentication.getPrincipal();
        int userId = claimsDto.getId();
        ratingService.addComment(hotelRatingRequest, userId);
    }


}


