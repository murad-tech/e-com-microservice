package com.murad.customer_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.murad.customer_service.models.Address;
import com.murad.customer_service.models.Customer;

import java.util.List;
import java.util.Set;

public record CustomerResponse(
    Long id,
    String firstName,
    String lastName,
    String email,
    String phone,
    Set<Address> addresses
) {
    public CustomerResponse(Customer customer) {
        this(customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddresses());
    }
}
