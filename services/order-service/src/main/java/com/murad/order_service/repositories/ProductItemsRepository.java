package com.murad.order_service.repositories;

import com.murad.order_service.models.ProductItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductItemsRepository extends JpaRepository<ProductItems, Long> {
    @Query("SELECT p FROM ProductItems p WHERE p.order.id = ?1")
    List<ProductItems> findOrderItemsByOrderId(Long id);
}
