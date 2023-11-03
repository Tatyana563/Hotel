package com.hotel.service.security;

import com.hotel.exception_handler.exception.HotelNotFoundException;
import com.hotel.model.dto.ClaimsDto;
import com.hotel.model.entity.Hotel;
import com.hotel.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OwnershipCheck {
    private final HotelRepository hotelRepository;

    public boolean check(int id, Authentication authentication) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        ClaimsDto principal = (ClaimsDto) authentication.getPrincipal();
        Hotel hotel = optionalHotel.orElseThrow(() -> new HotelNotFoundException(id));
        return hotel.getUserId().equals(principal.getId());
    }
}
