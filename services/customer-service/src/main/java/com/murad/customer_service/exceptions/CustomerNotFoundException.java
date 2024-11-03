package com.murad.customer_service.exceptions;

public class CustomerNotFoundException extends RuntimeException {

    /**
     * @param attribute - the attribute of the customer
     */
    public CustomerNotFoundException(String attribute) {
        super("Could not find customer: " + attribute);
    }
}
