package com.hotel.exception_handler.exception;

//TODO :
// OR  unique email validator add
public class UserAlreadyCreatedException extends RuntimeException {
//    public UserAlreadyCreated(String email) {
//        super(String.format("The user has already been created with email: %s", email));
//    }


    public UserAlreadyCreatedException(String email, String login) {
        super(email==null|| email.isEmpty()
                ? String.format("The user has already been created with login: %s", login)
                : String.format("The user has already been created with email: %s", email));
    }
}

