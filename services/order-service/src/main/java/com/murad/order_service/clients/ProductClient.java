package com.murad.order_service.clients;

import com.murad.order_service.clients.dto.InventoryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/inventory/{productId}")
    Integer getInventoryQuantity(@PathVariable Long productId);

    @PostMapping("/inventory")
    void setInventoryQuantity(@RequestBody InventoryRequest request);
}
