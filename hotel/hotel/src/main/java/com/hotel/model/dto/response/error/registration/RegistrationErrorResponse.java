package com.hotel.model.dto.response.error.registration;

import com.hotel.model.dto.response.error.ErrorResponse;
import com.hotel.model.dto.response.error.ErrorScope;

public class RegistrationErrorResponse extends ErrorResponse<RegistrationErrorPayload> {
    public RegistrationErrorResponse(RegistrationErrorPayload payload) {
        super("Failed to register user", ErrorScope.REGISTRATION, payload);
    }

    public static RegistrationErrorResponse of(RegistrationErrorPayload payload) {
        return new RegistrationErrorResponse(payload);
    }
    public static RegistrationErrorResponse of(boolean enabled) {
        return new RegistrationErrorResponse(new RegistrationErrorPayload(enabled));
    }
}
