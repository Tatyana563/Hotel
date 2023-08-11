package com.hotel.exception_handler;

import lombok.Getter;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
@Getter
public class AbstractNotFoundException extends ResourceNotFoundException {
    private final Integer resourceId;

    public AbstractNotFoundException( String message,Integer resourceId) {
        super(message);
        this.resourceId = resourceId;

    }

    public AbstractNotFoundException(Integer resourceId) {
        super("Generic resource not found");
        this.resourceId = resourceId;

    }
}
