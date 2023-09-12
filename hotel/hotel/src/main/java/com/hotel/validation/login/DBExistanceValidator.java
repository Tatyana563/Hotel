package com.hotel.validation.login;

import com.hotel.model.dto.request.RegistrationRequest;
import com.hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class DBExistanceValidator implements ConstraintValidator<RegistrationEmail, RegistrationRequest> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(RegistrationRequest registrationRequest, ConstraintValidatorContext context) {
        return !userRepository.existsByEmail(registrationRequest.getEmail());
    }
}