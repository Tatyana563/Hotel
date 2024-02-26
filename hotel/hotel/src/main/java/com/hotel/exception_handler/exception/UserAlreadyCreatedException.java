package com.hotel.exception_handler.exception;

import lombok.Getter;

@Getter
public class UserAlreadyCreatedException extends RuntimeException {
    private final String email;
    private final boolean enabled;

    public UserAlreadyCreatedException(String email, boolean enabled) {
        super(String.format("The user has already been created with email: %s", email));
        this.email = email;
        this.enabled = enabled;
    }
}

