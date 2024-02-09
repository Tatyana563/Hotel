package com.hotel.events.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordResetEvent {
    private String resetToken;
    private String email;
}
