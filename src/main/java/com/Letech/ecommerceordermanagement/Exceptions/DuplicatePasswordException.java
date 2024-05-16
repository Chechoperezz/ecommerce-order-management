package com.Letech.ecommerceordermanagement.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicatePasswordException extends ResponseStatusException {
    public DuplicatePasswordException(String message) {
        super(HttpStatus.CONFLICT,message);
    }
}
