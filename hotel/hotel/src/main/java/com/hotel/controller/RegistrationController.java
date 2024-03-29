package com.hotel.controller;


import com.hotel.model.dto.request.RegistrationRequest;
import com.hotel.model.dto.request.ShortRegistrationRequest;
import com.hotel.model.dto.response.NotifyAgainResponse;
import com.hotel.model.entity.User;
import com.hotel.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userRegistrationService;

    @PostMapping
    public User register(@RequestBody RegistrationRequest request) {
        return userRegistrationService.register(request);
    }

    @PostMapping(value = "/{token}/confirm")
    public void confirm(@PathVariable UUID token) {
        userRegistrationService.confirmRegistration(token);
    }

    // CASE#1 user wants to register, token in DB. Try to register again. Token can be  valid or invalid.
    @PostMapping("/resendConfirmationToken")
    public NotifyAgainResponse resendConfirmationToken(@RequestBody ShortRegistrationRequest request) {

        userRegistrationService.resendRegistrationTokenRequest(request);
        return new NotifyAgainResponse();
    }

}
