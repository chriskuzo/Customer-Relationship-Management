package com.mahwi.backend.vendor.repository;

import com.mahwi.backend.vendor.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on Vendor entities.
 */
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    boolean existsByName(String name);
}
