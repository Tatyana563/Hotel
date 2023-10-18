package com.hotel.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserConfirmRegistrationResponse {

    private String message;
    private UserRequestConfirmationStatus status;
}
