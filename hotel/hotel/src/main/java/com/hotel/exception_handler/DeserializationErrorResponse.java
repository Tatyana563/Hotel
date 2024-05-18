package com.hotel.exception_handler;

import com.hotel.model.dto.response.ErrorTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

@SuperBuilder
@Getter
public class DeserializationErrorResponse extends ErrorResponse {
    private String targetType;
    private String path;

    public DeserializationErrorResponse(String targetType, String path) {
        super(ErrorTypeEnum.DESERIALIZATION_ERROR);
        this.targetType = targetType;
        this.path = path;
    }

    public DeserializationErrorResponse() {
        super(ErrorTypeEnum.DESERIALIZATION_ERROR);

    }
}
