package com.mahwi.backend.catalog.dto;

import com.mahwi.backend.catalog.model.Product;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Product name is required") String name,
        @Size(max = 100, message = "Category name cannot exceed 100 characters") String category,
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
        BigDecimal price,
        @NotNull(message = "Vendor ID is required") Long vendorId,
        Boolean active
) {
    public Product toEntity() {
        Product p = new Product();
        p.setName(name);
        p.setCategory(category);
        p.setPrice(price);
        p.setVendorId(vendorId);
        p.setActive(active != null ? active : true);
        return p;
    }
}
