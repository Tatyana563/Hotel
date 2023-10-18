package com.hotel.exception_handler;

import java.util.UUID;

// TODO : add to handler
public class TokenExpirationException extends AbstractNotFoundException {
    public TokenExpirationException(UUID tokenId) {

        super("Token:" + tokenId + "is expired:");
    }
}

