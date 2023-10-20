package com.hotel.controller;


import com.hotel.model.dto.request.AuthRequest;
import com.hotel.model.dto.request.RegistrationRequest;
import com.hotel.model.entity.User;
import com.hotel.service.JwtService;
import com.hotel.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class UserController {
    private final UserService userRegistrationService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @PostMapping
    public User register(@RequestBody RegistrationRequest request) {
        return userRegistrationService.register(request);
    }

    @PostMapping(value = "/{token}/confirm")
    public void confirm(@PathVariable UUID token) {
        userRegistrationService.confirmRegistration(token);
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}
