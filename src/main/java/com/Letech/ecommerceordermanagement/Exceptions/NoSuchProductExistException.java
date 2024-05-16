package com.Letech.ecommerceordermanagement.Exceptions;

public class NoSuchProductExistException extends RuntimeException{

    private String message;

    public NoSuchProductExistException(){}

    public NoSuchProductExistException(String msg){
        super(msg);
        this.message=msg;
    }
}
