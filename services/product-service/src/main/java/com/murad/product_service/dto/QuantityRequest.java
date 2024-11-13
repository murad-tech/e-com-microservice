package com.murad.product_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record QuantityRequest(
        @NotBlank
        Long productId,

        @NotBlank
        @Min(0)
        Integer quantity
) {
}
