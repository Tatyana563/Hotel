package com.hotel.validation.hotelId;

import com.hotel.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class HotelIdValidator implements ConstraintValidator<HotelId, Integer> {

    private final HotelRepository hotelRepository;

    @Override
    public boolean isValid(Integer hotelId, ConstraintValidatorContext context) {
        return hotelRepository.existsById(hotelId);

    }
}