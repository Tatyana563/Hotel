package com.hotel.exception_handler;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
//TODO :
// OR  unique email validator add
public class UserAlreadyCreated extends RuntimeException {
//    public UserAlreadyCreated(String email) {
//        super(String.format("The user has already been created with email: %s", email));
//    }


    public UserAlreadyCreated(String email, String login) {
        super(email==null|| email.isEmpty()
                ? String.format("The user has already been created with login: %s", login)
                : String.format("The user has already been created with email: %s", email));
    }
}

