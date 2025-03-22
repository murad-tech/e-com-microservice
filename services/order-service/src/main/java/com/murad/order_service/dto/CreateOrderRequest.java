package com.murad.order_service.dto;

import com.murad.order_service.models.OrderStatus;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(
        @NotNull(message = "User id is required")
        Long userId,

        @NotNull(message = "Address id is required")
        Long addressId,

        @NotNull(message = "Order items is required")
        List<ProductItemsRequest> orderItems
) {}
