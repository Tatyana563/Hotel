package com.hotel.model.dto.request;

import com.hotel.validation.password.ValidPassword;
import lombok.Data;

@Data
public class ResetPasswordDTO {
    private String token;
    @ValidPassword
    private String password;
}
