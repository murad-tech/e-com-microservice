package com.murad.order_service.dto;

import com.murad.order_service.models.OrderStatus;

import java.util.List;

public record OrderRequest(
        Long userId,
        Long addressId,
        OrderStatus orderStatus,
        String paymentId,
        List<ProductItemsRequest> orderItems
) {}
