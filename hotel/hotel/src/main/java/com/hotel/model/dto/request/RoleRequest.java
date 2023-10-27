package com.hotel.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class RoleRequest {
    @NotEmpty
    private String name;
}
