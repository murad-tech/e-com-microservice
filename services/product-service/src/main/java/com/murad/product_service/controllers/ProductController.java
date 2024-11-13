package com.murad.product_service.controllers;

import com.murad.product_service.dto.ProductRequest;
import com.murad.product_service.models.Product;
import com.murad.product_service.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // TODO: Add authorization: only admin can create products
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductRequest request) throws URISyntaxException {
        var product = productService.createProduct(request);
        return ResponseEntity.created(new URI("/products/" + product.getId()))
                .body("Product created successfully with id " + product.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(productService.getProduct(id));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok()
                .body(productService.getAllProducts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok()
                .body(productService.updateProduct(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> patchProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok()
                .body(productService.patchProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
