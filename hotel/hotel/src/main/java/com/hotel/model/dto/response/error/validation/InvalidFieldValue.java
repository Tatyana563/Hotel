package com.hotel.model.dto.response.error.validation;

import lombok.Data;

@Data
public class InvalidFieldValue {
    private String path;
    private String fieldName;
    private String message;
    private Object rejectedValue;
}
