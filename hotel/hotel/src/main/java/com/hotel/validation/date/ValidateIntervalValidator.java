package com.hotel.validation.date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateIntervalValidator implements ConstraintValidator<ValidateInterval, DateInterval> {

    @Override
    public boolean isValid(final DateInterval value, final ConstraintValidatorContext context) {
        try {

            return (value.getFirstDate() != null && value.getLastDate() != null)
                    && (value.getFirstDate().before(value.getLastDate()));
        } catch (final Exception e) {
            e.printStackTrace();

            return false;
        }
    }
}