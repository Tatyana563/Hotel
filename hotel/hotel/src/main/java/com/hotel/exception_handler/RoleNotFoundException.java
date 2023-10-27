package com.hotel.exception_handler;

public class RoleNotFoundException extends AbstractNotFoundException {
    public RoleNotFoundException(String role) {

        super("Role  " + role + " doesn't exist");
    }
}

