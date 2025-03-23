package com.murad.order_service.clients.dto;

public record InventoryRequest(
    Long productId,
    Integer quantity
) {
}
