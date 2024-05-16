package com.Letech.ecommerceordermanagement.Exceptions;

public class CustomerNotAdultException extends RuntimeException {

    private String message;


    public CustomerNotAdultException(String msg){
        super(msg);
        this.message=msg;
    }

}

