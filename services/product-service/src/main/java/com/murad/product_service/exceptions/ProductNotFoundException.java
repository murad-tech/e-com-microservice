package com.murad.product_service.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("Product not found");
    }

    public ProductNotFoundException(Long productId) {
        super("Could not find product: " + productId);
    }
}
