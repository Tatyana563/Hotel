package com.hotel.validation.date;

import com.hotel.model.dto.request.SearchRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CompareDateValidator implements ConstraintValidator<CompareDate, SearchRequest> {

    @Override
    public boolean isValid(final SearchRequest value, final ConstraintValidatorContext context) {
        try {

            return (value.getCheckIn() != null && value.getCheckOut() != null) &&
                    (value.getCheckIn().before(value.getCheckOut()));
        } catch (final Exception e) {
            e.printStackTrace();

            return false;
        }
    }
}