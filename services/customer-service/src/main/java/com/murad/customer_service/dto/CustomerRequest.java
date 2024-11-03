package com.murad.customer_service.dto;

import com.murad.customer_service.models.Address;
import com.murad.customer_service.models.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

public record CustomerRequest(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @Email
        @NotBlank
        String email,

        @Pattern(regexp = "^\\d{10}$")
        String phone,

        Set<Address> addresses
) {
    public Customer toCustomer() {
        return Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .addresses(addresses)
                .build();
    }
}
