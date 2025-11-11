package com.mahwi.backend.catalog.dto;

import com.mahwi.backend.catalog.model.Product;
import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String category,
        BigDecimal price,
        Boolean active,
        Long vendorId
) {
    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getActive(),
                product.getVendorId()
        );
    }
}
