package com.mahwi.backend.customer.repository;

import com.mahwi.backend.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for accessing customer data.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByVendorId(Long vendorId);
}
