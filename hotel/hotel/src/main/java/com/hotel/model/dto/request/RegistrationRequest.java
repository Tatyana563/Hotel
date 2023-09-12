package com.hotel.model.dto.request;

import com.hotel.validation.login.RegistrationEmail;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@RegistrationEmail
public class RegistrationRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String login;
    @NotEmpty
    private String phone;
    @NotEmpty
    private String email;
}
