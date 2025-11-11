package com.mahwi.backend.catalog.repository;

import com.mahwi.backend.catalog.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

/**
 * Utility class providing JPA Specifications for dynamic Product queries.
 */
public final class ProductSpecifications {

    private ProductSpecifications() {
        // prevent instantiation
    }

    public static Specification<Product> nameContains(String text) {
        return (root, query, cb) ->
                (text == null || text.isBlank())
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("name")), "%" + text.toLowerCase() + "%");
    }

    public static Specification<Product> minPrice(BigDecimal min) {
        return (root, query, cb) ->
                (min == null)
                        ? cb.conjunction()
                        : cb.greaterThanOrEqualTo(root.get("price"), min);
    }

    public static Specification<Product> maxPrice(BigDecimal max) {
        return (root, query, cb) ->
                (max == null)
                        ? cb.conjunction()
                        : cb.lessThanOrEqualTo(root.get("price"), max);
    }

    public static Specification<Product> active(Boolean active) {
        return (root, query, cb) ->
                (active == null)
                        ? cb.conjunction()
                        : cb.equal(root.get("active"), active);
    }

    public static Specification<Product> vendorIdEquals(Long vendorId) {
        return (root, query, cb) ->
                (vendorId == null)
                        ? cb.conjunction()
                        : cb.equal(root.get("vendorId"), vendorId);
    }
}
