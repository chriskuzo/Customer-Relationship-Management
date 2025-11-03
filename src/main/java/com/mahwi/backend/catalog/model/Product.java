package com.mahwi.backend.catalog.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity representing a product belonging to a vendor's catalog.
 * Products contain category, pricing, and activation status.
 */
@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String category;
    private Double price;
    private Boolean isActive = true;

    /**
     * Each product is tied to a specific vendor.
     */
    private Long vendorId;
}
