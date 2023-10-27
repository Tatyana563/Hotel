package com.hotel.controller;


import com.hotel.model.dto.request.RegistrationRequest;
import com.hotel.model.entity.User;
import com.hotel.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class UserController {
    private final UserService userRegistrationService;

    @PostMapping
    public User register(@RequestBody RegistrationRequest request) {
        return userRegistrationService.register(request);
    }

    @PostMapping(value = "/{token}/confirm")
    public void confirm(@PathVariable UUID token) {
        userRegistrationService.confirmRegistration(token);
    }

}
// TODO: 2 controllers - 1 for log in 2 for registration