package com.hotel.events.model;

import com.hotel.confirmation.VerificationToken;
import com.hotel.model.dto.request.RegistrationRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistrationEvent {
    private RegistrationRequest request;
    private VerificationToken token;
    private String verificationUrl;
}
