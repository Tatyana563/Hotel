package com.hotel.controller;


import com.hotel.model.dto.request.ResetPasswordDTO;
import com.hotel.service.api.UserService;
import com.hotel.validation.password.ValidPassword;
import jakarta.validation.Valid;
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
    @PostMapping(value = "/reset")
    public void createNewPassword(@RequestBody @Valid ResetPasswordDTO resetPasswordDTO) {
        userRegistrationService.createNewPassword(resetPasswordDTO.getToken(), resetPasswordDTO.getToken());
    }

}
//TODO: check