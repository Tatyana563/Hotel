package com.hotel.exception_handler.exception;

import com.hotel.exception_handler.exception.AbstractNotFoundException;

import java.util.UUID;

// TODO : add to handler
public class TokenExpirationException extends AbstractNotFoundException {
    public TokenExpirationException(UUID tokenId) {

        super("Token:" + tokenId + "is expired:");
    }
}

