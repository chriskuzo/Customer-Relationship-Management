package com.mahwi.backend.catalog.repository;

import com.mahwi.backend.catalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for managing Product entities.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByVendorId(Long vendorId);

    boolean existsByNameAndVendorId(String name, Long vendorId);
}
