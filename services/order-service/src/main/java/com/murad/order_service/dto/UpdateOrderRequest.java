package com.murad.order_service.dto;

import com.murad.order_service.models.OrderStatus;

import java.util.List;

public record UpdateOrderRequest(
        Long addressId,
        OrderStatus orderStatus,
        String paymentId,
        List<ProductItemsRequest> orderItems
) {}
