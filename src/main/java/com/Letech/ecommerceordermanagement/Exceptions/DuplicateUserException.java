package com.Letech.ecommerceordermanagement.Exceptions;

public class DuplicateUserException  extends RuntimeException{
    public DuplicateUserException(String message){
        super(message);
    }
}