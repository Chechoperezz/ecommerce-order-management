package com.Letech.ecommerceordermanagement.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicateEmailException extends ResponseStatusException {
    public DuplicateEmailException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
