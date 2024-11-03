package com.murad.customer_service.dto;

import com.murad.customer_service.models.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record CustomerUpdateRequest(
    String firstName,
    String lastName,

    @Email
    String email,

    @Pattern(regexp = "^\\d{10}$")
    String phone
) {
}
