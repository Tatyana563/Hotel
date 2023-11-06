package com.hotel.exception_handler.exception;

import lombok.Getter;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
@Getter
public class AbstractNotFoundException extends RuntimeException {
    private final String resourceId;

    public AbstractNotFoundException( String message,String resourceId) {
        super(message);
        this.resourceId = resourceId;

    }

    public AbstractNotFoundException(String resourceId) {
        super("Generic resource not found");
        this.resourceId = resourceId;

    }
}