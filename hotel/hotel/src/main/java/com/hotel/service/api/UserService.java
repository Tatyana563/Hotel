package com.hotel.service.api;

import com.hotel.model.dto.request.RegistrationRequest;
import com.hotel.model.dto.request.ShortRegistrationRequest;
import com.hotel.model.dto.response.NotifyAgainResponse;
import com.hotel.model.entity.User;

import java.util.UUID;

public interface UserService {
    User register(RegistrationRequest request);

    void reset(String email);

    void createNewPassword(String token, String password);

    NotifyAgainResponse resendRegistrationTokenRequest(ShortRegistrationRequest request);

    void confirmRegistration(UUID token);
}
