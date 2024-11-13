package com.murad.product_service.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "inventories")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer quantity;

    @OneToOne(mappedBy = "inventory", orphanRemoval = true)
    private Product product;
}
