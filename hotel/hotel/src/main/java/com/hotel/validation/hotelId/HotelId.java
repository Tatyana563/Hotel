package com.hotel.validation.hotelId;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = HotelIdValidator.class)
@Target({ElementType.PARAMETER, TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface HotelId {
    String message() default "The hotel was not found in DB";//local, Front fills value according to language

    Class<?>[] groups() default {};//group by different level of validation, imediately (date is not valid) or look at the DB

    Class<? extends Payload>[] payload() default {};//


}