package com.hotel.validation.date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Duration;


public class ValidateCustomIntervalValidator implements ConstraintValidator<ValidCustomInterval, DateInterval> {
    private String firstDateFieldName;
    private String lastDateFieldName;

    @Override
    public void initialize(ValidCustomInterval constraintAnnotation) {
        firstDateFieldName = constraintAnnotation.firstDateFieldName();
        lastDateFieldName = constraintAnnotation.lastDateFieldName();
    }

    @Override
    public boolean isValid(final DateInterval value, final ConstraintValidatorContext context) {
        boolean atLeastOneDateIsNull = false;

        if (value.getFirstDate() == null) {
            atLeastOneDateIsNull = true;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("first date should not be null")
                    .addPropertyNode(firstDateFieldName)
                    .addConstraintViolation();

        }
        if (value.getLastDate() == null) {
            atLeastOneDateIsNull = true;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("last date should not be null")
                    .addPropertyNode(lastDateFieldName)
                    .addConstraintViolation();
        }
        if (atLeastOneDateIsNull) {
            return false;
        }

        if (value.getFirstDate().isAfter(value.getLastDate())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("first date should be before last date")
                    .addBeanNode()
                    .addConstraintViolation();
            return false;
        }
        Duration duration = Duration.between(value.getFirstDate(), value.getLastDate());
        Duration durationOf7Days = Duration.ofDays(7);
        Duration durationOf10Days = Duration.ofDays(10);
        if (duration.minus(durationOf7Days).isNegative()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("selected duration is less than 7 days")
                    .addBeanNode()
                    .addConstraintViolation();

            return false;
        }
        if (durationOf10Days.minus(duration).isNegative()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("selected duration is more than 10 days")
                    .addBeanNode()

                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}

