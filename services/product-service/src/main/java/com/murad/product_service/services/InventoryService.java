package com.murad.product_service.services;

import com.murad.product_service.dto.QuantityRequest;
import com.murad.product_service.exceptions.ProductNotFoundException;
import com.murad.product_service.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public int getInventoryQuantity(Long productId) {
        var inventory = inventoryRepository.findByProductId(productId).orElseThrow(ProductNotFoundException::new);
        return inventory.getQuantity();
    }

    public void updateInventory(QuantityRequest request) {
        var inventory = inventoryRepository.findByProductId(request.productId()).orElseThrow(ProductNotFoundException::new);
        inventory.setQuantity(request.quantity());

        inventoryRepository.save(inventory);
    }
}
