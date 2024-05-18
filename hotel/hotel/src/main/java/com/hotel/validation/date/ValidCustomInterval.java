package com.hotel.validation.date;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidateCustomIntervalValidator.class)
@Target({ElementType.PARAMETER, TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface ValidCustomInterval {
    String message() default "The date interval is not from 7 to 10 days";//local, Front fills value according to language

    Class<?>[] groups() default {};//group by different level of validation, imediately (date is not valid) or look at the DB

    Class<? extends Payload>[] payload() default {};//

    String firstDateFieldName();
    String lastDateFieldName();

}