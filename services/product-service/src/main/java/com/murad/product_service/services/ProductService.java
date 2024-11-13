package com.murad.product_service.services;

import com.murad.product_service.dto.ProductRequest;
import com.murad.product_service.exceptions.ProductNotFoundException;
import com.murad.product_service.models.Inventory;
import com.murad.product_service.models.Product;
import com.murad.product_service.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Product createProduct(ProductRequest request) {
        var category = categoryService.getCategoryById(request.categoryId());
        var inventory = Inventory.builder().quantity(request.quantity()).build();

        var product = Product.builder()
                .brand(request.brand())
                .model(request.model())
                .description(request.description())
                .price(request.price())
                .image(request.image())
                .category(category)
                .inventory(inventory)
                .build();

        return productRepository.save(product);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Long id, ProductRequest request) {
        var product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        var category = categoryService.getCategoryById(request.categoryId());

        product.setBrand(request.brand());
        product.setModel(request.model());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setImage(request.image());
        product.setCategory(category);
        product.getInventory().setQuantity(request.quantity());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product patchProduct(Long id, ProductRequest request) {
        var product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        if (request.brand() != null) product.setBrand(request.brand());
        if (request.model() != null) product.setModel(request.model());
        if (request.description() != null) product.setDescription(request.description());
        if (request.price() != null) product.setPrice(request.price());
        if (request.image() != null) product.setImage(request.image());
        if (request.categoryId() != null) product.setCategory(categoryService.getCategoryById(request.categoryId()));
        if (request.quantity() != 0) {
            product.getInventory().setQuantity(request.quantity());
        }

        return productRepository.save(product);
    }
}
