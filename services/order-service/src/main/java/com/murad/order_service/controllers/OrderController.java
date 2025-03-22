package com.murad.order_service.controllers;

import com.murad.order_service.dto.CreateOrderRequest;
import com.murad.order_service.dto.UpdateOrderRequest;
import com.murad.order_service.models.Order;
import com.murad.order_service.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.findOrder(orderId));
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody @Valid CreateOrderRequest request) throws URISyntaxException {
        var order = orderService.saveOrder(request);
        return ResponseEntity.created(new URI("/orders/" + order.getId()))
                .body("Order successfully placed with id " + order.getId());
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody @Valid UpdateOrderRequest request) {
        var order = orderService.updateOrder(orderId, request);
        return ResponseEntity.ok(order);
    }
}
