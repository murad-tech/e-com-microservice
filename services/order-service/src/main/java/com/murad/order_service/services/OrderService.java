package com.murad.order_service.services;

import com.murad.order_service.clients.CustomerClient;
import com.murad.order_service.clients.ProductClient;
import com.murad.order_service.clients.dto.InventoryRequest;
import com.murad.order_service.dto.CreateOrderRequest;
import com.murad.order_service.dto.ProductItemsRequest;
import com.murad.order_service.dto.UpdateOrderRequest;
import com.murad.order_service.models.Order;
import com.murad.order_service.models.OrderStatus;
import com.murad.order_service.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductItemsService productItemsService;

    private final CustomerClient customerClient;
    private final ProductClient productClient;

    public Order findOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found")); // TODO: Handle exception
    }

    public Order saveOrder(CreateOrderRequest request) {
        // Verify customer and address exists
        var customer = customerClient.findCustomerById(request.userId())
                .orElseThrow(() -> new RuntimeException("Customer not found")); // TODO: Handle exception
        if (customer.addresses().stream().filter(a -> a.id().equals(request.addressId())).count() != 1)
            throw new RuntimeException("Address not found");
        // Verify enough inventories for each product
        List<InventoryRequest> updateInventory = new ArrayList<>();
        request.orderItems().forEach(p -> {
            var inventory = productClient.getInventoryQuantity(p.productId());
            if (p.quantity() > inventory)
                throw new RuntimeException("Not enough inventory of product: " + p.productId()); // TODO: Handle exception
            updateInventory.add(new InventoryRequest(p.productId(), inventory - p.quantity()));
        });

        updateInventory.forEach(productClient::setInventoryQuantity);

        var orderRequest = Order.builder()
                .userId(request.userId())
                .addressId(request.addressId())
                .orderStatus(OrderStatus.PENDING)
                .build();

        var order = orderRepository.save(orderRequest);
        productItemsService.saveProductItems(request.orderItems(), order);

        return order;
    }

    public Order updateOrder(Long orderId, UpdateOrderRequest request) {
        // TODO: verify address exists
        // TODO: verify product exists and inventory
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

    private void mergeOrder(Order order, UpdateOrderRequest request) {
        order.setAddressId(request.addressId() != null ? request.addressId() : order.getAddressId());
        order.setOrderStatus(request.orderStatus() != null ? request.orderStatus() : order.getOrderStatus());
    }
}
