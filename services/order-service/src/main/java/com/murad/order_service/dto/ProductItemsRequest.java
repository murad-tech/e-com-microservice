package com.murad.order_service.dto;

import com.murad.order_service.models.Order;
import com.murad.order_service.models.ProductItems;

public record ProductItemsRequest(
    Long productId,
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
