package com.hotel.exception_handler.exception;

import java.util.UUID;

public class TokenExpirationException extends AbstractNotFoundException {
    public TokenExpirationException(UUID tokenId) {

        super("Token:" + tokenId + "is expired:");
    }
}

