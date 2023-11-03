package com.hotel.exception_handler.exception;

import com.hotel.exception_handler.exception.AbstractNotFoundException;

public class RoleNotFoundException extends AbstractNotFoundException {
    public RoleNotFoundException(String role) {

        super("Role  " + role + " doesn't exist");
    }
}

