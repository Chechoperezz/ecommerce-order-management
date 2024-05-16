package com.Letech.ecommerceordermanagement.Exceptions;

public class StockItemNotFoundException extends RuntimeException {
    public StockItemNotFoundException(String message) {
        super(message);
    }
}
