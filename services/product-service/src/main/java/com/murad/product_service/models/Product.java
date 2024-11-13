package com.murad.product_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "products")
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column
    private String model;

    @Column
    private String description;

    @Column
    private String price;

    @Column
    private String image;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

}
