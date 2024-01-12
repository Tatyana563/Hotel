package com.hotel.model.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class AllowRequestDTO {
    private final boolean isAllowed;
    private final long timeToWait;
}
