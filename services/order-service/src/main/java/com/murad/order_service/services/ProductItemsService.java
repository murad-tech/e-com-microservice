package com.murad.order_service.services;

import com.murad.order_service.dto.ProductItemsRequest;
import com.murad.order_service.models.Order;
import com.murad.order_service.models.ProductItems;
import com.murad.order_service.repositories.ProductItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductItemsService {
    private final ProductItemsRepository productItemsRepository;

    public void saveProductItems(List<ProductItemsRequest> request, Order order) {
        List<ProductItems> productItemsList = request.stream().map(r -> r.toProductItems(order)).toList();
        productItemsRepository.saveAll(productItemsList);
    }

    public void deleteProductItemsOfOrder(Order order) {
        List<ProductItems> orderItems = productItemsRepository.findOrderItemsByOrderId(order.getId());
        productItemsRepository.deleteAll(orderItems);
    }
}
