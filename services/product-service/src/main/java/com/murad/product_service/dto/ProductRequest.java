package com.murad.product_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        @NotBlank
        String brand,

        @NotBlank
        String model,

        String description,

        @NotBlank
        String price,

        String image,

        @NotNull
        Long categoryId,

        @Min(0)
        Integer quantity
) {
}
