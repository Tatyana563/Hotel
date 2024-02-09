package com.hotel.controller;


import com.hotel.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class ResetPasswordController {
    private final UserService userRegistrationService;

    @PostMapping("/reset-request")
    public void reset(@RequestBody String email) {
        userRegistrationService.reset(email);
    }

    //TODO: validate password complexity, @Min, @Max, @Pattern (if 3 of them -> dto) or custom annotation here
    @PostMapping(value = "/reset/{token}")
    public void createNewPassword(@PathVariable String token, @RequestBody String password) {
        userRegistrationService.createNewPassword(token, password);
    }

}
