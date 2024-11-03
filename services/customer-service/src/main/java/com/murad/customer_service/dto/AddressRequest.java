package com.murad.customer_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressRequest(
    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]\\d\\.$", message = "Invalid address format")
    @Size(min = 5, max = 50)
    String addressLine1,

    @Size(min = 5, max = 50)
    String addressLine2,

    @NotBlank
    String city,

    @NotBlank
    @Pattern(regexp = "^[A-Z]{2}$")
    String state,

    @NotBlank
    @Pattern(regexp = "^\\d{5}$")
    String zipCode
) {
}
