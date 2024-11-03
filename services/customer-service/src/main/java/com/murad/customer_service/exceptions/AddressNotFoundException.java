package com.murad.customer_service.exceptions;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException() {
        super("Address not found in customer's address list");
    }
    public AddressNotFoundException(String message) {
        super(message);
    }
}
