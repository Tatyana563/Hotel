package com.hotel.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String username;
    private String password;
    @Override
    public String toString() {
        return "AuthRequest(username=" + username + ")";
    }
}