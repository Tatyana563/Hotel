package com.hotel.exception_handler;

import com.hotel.exception_handler.exception.ValidationErrorDTO;
import com.hotel.model.dto.response.ErrorTypeEnum;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@Getter
public abstract class ErrorResponse {

   private final ErrorTypeEnum errorTypeEnum;

    protected ErrorResponse(ErrorTypeEnum errorTypeEnum) {
        this.errorTypeEnum = errorTypeEnum;
    }
//@Builder.Default
  //  private LocalDateTime localDateTime = LocalDateTime.now();
    private String message;

}
