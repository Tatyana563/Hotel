package com.hotel.exception_handler;

import com.hotel.model.dto.response.ErrorTypeEnum;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class CommonErrorResponse extends ErrorResponse {
    protected CommonErrorResponse() {
        super(ErrorTypeEnum.COMMON_ERROR);
    }
}
