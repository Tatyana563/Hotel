package com.hotel.service.security;

import com.hotel.model.dto.ClaimsDto;
import com.hotel.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HotelOwnershipCheckService {
    private final HotelRepository hotelRepository;

    public boolean checkOwnershipForDelete(int id, Authentication authentication) {
        ClaimsDto principal = (ClaimsDto) authentication.getPrincipal();
        int userId = principal.getId();
        return hotelRepository.existsByIdAndOwnerId(id, userId);

    }
}

