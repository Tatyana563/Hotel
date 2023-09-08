package com.hotel.service;

import com.hotel.model.dto.request.RegistrationRequest;
import com.hotel.model.entity.User;

public interface UserService {
    User register(RegistrationRequest request);
}
