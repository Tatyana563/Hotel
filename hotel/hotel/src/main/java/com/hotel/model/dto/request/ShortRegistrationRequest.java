package com.hotel.model.dto.request;

import com.hotel.validation.login.RegistrationEmail;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
@RegistrationEmail
public class ShortRegistrationRequest {

    @NotEmpty
    private String password;
    @NotEmpty
    private String login;
    @NotEmpty
    private String email;

}
