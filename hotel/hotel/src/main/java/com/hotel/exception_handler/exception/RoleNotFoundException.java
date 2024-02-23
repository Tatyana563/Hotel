package com.hotel.exception_handler.exception;

public class RoleNotFoundException extends AbstractNotFoundException {
    public RoleNotFoundException(String role) {

        super("Role  " + role + " doesn't exist");
    }
}

