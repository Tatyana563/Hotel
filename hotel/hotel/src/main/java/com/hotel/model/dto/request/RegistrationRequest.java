package com.hotel.model.dto.request;

import com.hotel.model.entity.Role;
import com.hotel.validation.login.RegistrationEmail;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;



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
    @NotEmpty
    private String role;
}
