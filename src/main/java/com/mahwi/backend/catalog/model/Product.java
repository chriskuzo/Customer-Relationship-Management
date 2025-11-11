package com.mahwi.backend.catalog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing a product in a vendor's catalog.
 * Includes category, pricing, stock, and activation status.
 */
@Data
@Entity
@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "vendor_id"})
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The name of the product (unique per vendor) */
    @NotBlank(message = "Product name is required")
    @Column(nullable = false)
    private String name;

    /** Optional category name */
    @Size(max = 100, message = "Category name cannot exceed 100 characters")
    private String category;

    /** Product price */
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid monetary value")
    @Column(nullable = false)
    private BigDecimal price;

    /** Whether the product is visible/active */
    @Column(nullable = false)
    private Boolean active = true;

    /** Each product belongs to a vendor */
    @NotNull(message = "Vendor ID is required")
    @Column(name = "vendor_id", nullable = false)
    private Long vendorId;

    /** Optional stock field (for future expansion) */
    @PositiveOrZero(message = "Stock cannot be negative")
    private Integer stock = 0;

    /** Auto-managed timestamps */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /** Optional product description */
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public String getDescription() { return description; }
    public boolean isActive() { return active; }
}
