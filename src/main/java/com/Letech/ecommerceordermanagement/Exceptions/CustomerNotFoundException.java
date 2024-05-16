package com.Letech.ecommerceordermanagement.Exceptions;

public class CustomerNotFoundException extends RuntimeException {
    private String msg;
    public CustomerNotFoundException(){}
    public CustomerNotFoundException(String msg) {
        super(msg);
        this.msg=msg;
    }
}
