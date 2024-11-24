package com.murad.order_service.services;

import com.murad.order_service.dto.OrderRequest;
import com.murad.order_service.dto.ProductItemsRequest;
import com.murad.order_service.models.Order;
import com.murad.order_service.models.OrderStatus;
import com.murad.order_service.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductItemsService productItemsService;

    public Order findOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found")); // TODO: Handle exception
    }

    public Order saveOrder(OrderRequest request) {
        var orderRequest = Order.builder()
                .userId(request.userId())
                .addressId(request.addressId())
                .orderStatus(OrderStatus.PENDING)
                .build();

        var order = orderRepository.save(orderRequest);
        productItemsService.saveProductItems(request.orderItems(), order);

        return order;
    }

    public Order updateOrder(Long orderId, OrderRequest request) {
        var order = findOrder(orderId);
        mergeOrder(order, request);
        if(request.orderItems() != null)
            updateProductItems(request.orderItems(), order);
        return orderRepository.save(order);
    }

    private void updateProductItems(List<ProductItemsRequest> productItems, Order order) {
        productItemsService.deleteProductItemsOfOrder(order);
        productItemsService.saveProductItems(productItems, order);
    }

    private void mergeOrder(Order order, OrderRequest request) {
        order.setAddressId(request.addressId() != null ? request.addressId() : order.getAddressId());
        order.setOrderStatus(request.orderStatus() != null ? request.orderStatus() : order.getOrderStatus());
        order.setPaymentId(request.paymentId() != null ? request.paymentId() : order.getPaymentId());
    }
}
