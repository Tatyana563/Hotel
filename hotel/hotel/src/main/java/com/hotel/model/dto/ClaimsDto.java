package com.hotel.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class ClaimsDto {
    private String username;
    private int id;
    private String role;
}
