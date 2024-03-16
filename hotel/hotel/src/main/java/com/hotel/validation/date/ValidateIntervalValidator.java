package com.hotel.validation.date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateIntervalValidator implements ConstraintValidator<ValidInterval, DateInterval> {

    @Override
    public boolean isValid(final DateInterval value, final ConstraintValidatorContext context) {


            return (value.getFirstDate() != null && value.getLastDate() != null)
                    && (value.getFirstDate().isBefore(value.getLastDate()));

        }
    }
