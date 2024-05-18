package com.hotel.exception_handler;

import com.hotel.exception_handler.exception.ValidationErrorDTO;
import com.hotel.model.dto.response.ErrorTypeEnum;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
@SuperBuilder
@Getter
public class ValidationErrorResponse extends ErrorResponse {
    private List<ValidationErrorDTO> validationErrors;
    public ValidationErrorResponse() {
        super(ErrorTypeEnum.VALIDATION_ERROR);
    }
}
