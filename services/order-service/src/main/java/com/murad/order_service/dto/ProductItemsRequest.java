package com.murad.order_service.dto;

import com.murad.order_service.models.Order;
import com.murad.order_service.models.ProductItems;
import jakarta.validation.constraints.NotNull;

public record ProductItemsRequest(
    @NotNull(message = "Product id is required")
    Long productId,

    @NotNull(message = "Quantity is required")
    Integer quantity
) {
    public ProductItems toProductItems(Order order) {
        return ProductItems.builder()
                .order(order)
                .productId(productId)
                .quantity(quantity)
                .build();
    }
}
