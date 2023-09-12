package com.hotel.validation.login;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = DBExistanceValidator.class)
@Target({ElementType.PARAMETER, TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface RegistrationEmail {
    String message() default "The use with the same email has already been registered DB";//local, Front fills value according to language

    Class<?>[] groups() default {};//group by different level of validation, imediately (date is not valid) or look at the DB

    Class<? extends Payload>[] payload() default {};//


}