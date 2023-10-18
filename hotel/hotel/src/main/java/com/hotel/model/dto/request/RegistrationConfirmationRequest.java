package com.hotel.model.dto.request;

import com.hotel.validation.login.RegistrationEmail;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@RegistrationEmail
public class RegistrationConfirmationRequest {
    @NotEmpty
    private String token;
}
