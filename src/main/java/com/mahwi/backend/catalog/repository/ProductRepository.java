package com.mahwi.backend.catalog.repository;

import com.mahwi.backend.catalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Product entities.
 * Supports CRUD, Specifications, and vendor-based lookups.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findByVendorId(Long vendorId);

    boolean existsByNameAndVendorId(String name, Long vendorId);
}
