package com.murad.product_service.controllers;

import com.murad.product_service.dto.QuantityRequest;
import com.murad.product_service.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<String> updateInventory(@RequestBody QuantityRequest request) {
        inventoryService.updateInventory(request);
        return ResponseEntity.ok("Quantity updated successfully");
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Integer> getInventoryQuantity(@PathVariable Long productId) {
        return ResponseEntity.ok().body(inventoryService.getInventoryQuantity(productId));
    }
}
