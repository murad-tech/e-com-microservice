package com.murad.order_service.repositories;

import com.murad.order_service.models.Order;
import com.murad.order_service.models.ProductItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductItemsRepository extends JpaRepository<ProductItems, Long> {
    @Query("SELECT p FROM ProductItems p WHERE p.order.id = ?1")
    List<ProductItems> findOrderItemsByOrderId(Long id);
}
