package com.hotel.model.dto.response.error.validation;

import com.hotel.model.dto.response.error.ErrorResponse;
import com.hotel.model.dto.response.error.ErrorScope;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ValidationErrorResponse extends ErrorResponse<List<InvalidFieldValue>> {
    public ValidationErrorResponse( List<InvalidFieldValue> payload) {
        super("Validation error", ErrorScope.VALIDATION, payload);
    }
}
