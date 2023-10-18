package com.hotel.controller;


import com.hotel.model.dto.request.RegistrationRequest;
import com.hotel.model.entity.User;
import com.hotel.service.api.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class UserRegistrationController {
    private final UserRegistrationService userRegistrationService;

    @PostMapping
    public User register(@ModelAttribute RegistrationRequest request) {
        return userRegistrationService.register(request);
    }

    @PostMapping(value = "/{token}/confirm")
    public void confirm(@PathVariable UUID token) {
        userRegistrationService.confirmRegistration(token);
    }
}
