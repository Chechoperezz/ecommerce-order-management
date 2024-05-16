package com.Letech.ecommerceordermanagement.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicatePhoneException extends ResponseStatusException {
    public DuplicatePhoneException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
