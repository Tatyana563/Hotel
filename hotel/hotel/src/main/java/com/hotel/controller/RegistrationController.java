package com.hotel.controller;


import com.hotel.events.model.UserConfirmedRegistrationEvent;
import com.hotel.model.dto.request.RegistrationRequest;
import com.hotel.model.dto.request.ShortRegistrationRequest;
import com.hotel.model.dto.response.NotifyAgainResponse;
import com.hotel.model.dto.response.UserConfirmRegistrationResponse;
import com.hotel.model.dto.response.UserRequestConfirmationStatus;
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
    public UserConfirmRegistrationResponse register(@RequestBody RegistrationRequest request) {
        User registeredUser = userRegistrationService.register(request);
        String message = String.format("User was sent email to the mailbox:%s with confirmation link to complete registration", registeredUser.getEmail());
        return new UserConfirmRegistrationResponse(message, UserRequestConfirmationStatus.REGISTRATION_CONFIRMED);
    }

    @PostMapping(value = "/{token}/confirm")
    public String confirm(@PathVariable UUID token) {
        UserConfirmedRegistrationEvent confirmedRegistrationEvent = userRegistrationService.confirmRegistration(token);
        return String.format("User %s with email %s was successfully confirmed",confirmedRegistrationEvent.getUsername(),confirmedRegistrationEvent.getEmail());
    }

    // CASE#1 user wants to register, token in DB. Try to register again. Token can be  valid or invalid.
    @PostMapping("/resendConfirmationToken")
    public NotifyAgainResponse resendConfirmationToken(@RequestBody ShortRegistrationRequest request) {

        userRegistrationService.resendRegistrationTokenRequest(request);
        return new NotifyAgainResponse();
    }

}
