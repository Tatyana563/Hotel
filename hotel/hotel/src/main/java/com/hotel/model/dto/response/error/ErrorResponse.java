package com.hotel.model.dto.response.error;

import lombok.Data;

import java.util.List;
@Data
public class ErrorResponse<P> {
    private String message;
    private ErrorScope errorScope;
    private P payload;

    public ErrorResponse(String message, ErrorScope errorScope, P payload) {
        this.message = message;
        this.errorScope = errorScope;
        this.payload = payload;
    }
}
