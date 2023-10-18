package com.hotel.service.api;

import com.hotel.model.dto.request.RegistrationRequest;
import com.hotel.model.entity.User;

import java.util.UUID;

public interface UserRegistrationService {
    User register(RegistrationRequest request);
    void confirmRegistration(UUID token);
}
