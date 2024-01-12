package com.hotel.exception_handler.exception;

import lombok.Getter;

//TODO :
// OR  unique email validator add
@Getter
public class UserAlreadyCreatedException extends RuntimeException {
    //    public UserAlreadyCreated(String email) {
//        super(String.format("The user has already been created with email: %s", email));
//    }
    private final String email;
    private final boolean enabled;

    public UserAlreadyCreatedException(String email, boolean enabled) {
        super("User with email: " + email + " has already been created");
        this.email = email;
        this.enabled = enabled;
    }
}

