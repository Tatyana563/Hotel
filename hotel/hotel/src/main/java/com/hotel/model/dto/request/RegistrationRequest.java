package com.hotel.model.dto.request;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String login;
    private String phone;
    private String email;
}
