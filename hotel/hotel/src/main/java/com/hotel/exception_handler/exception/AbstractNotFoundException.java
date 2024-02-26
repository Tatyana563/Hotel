package com.hotel.exception_handler.exception;

import lombok.Getter;

@Getter
public class AbstractNotFoundException extends RuntimeException {
    private final String resourceId;

    public AbstractNotFoundException(String resourceName, String resourceId) {
        super(String.format("%s with id %s was not found", resourceName, resourceId));
        this.resourceId = resourceId;

    }
}
