package com.hotel.events.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserConfirmedRegistrationEvent {
    private String username;
    private String email;
}
