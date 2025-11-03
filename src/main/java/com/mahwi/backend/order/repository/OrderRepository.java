package com.mahwi.backend.order.repository;

import com.mahwi.backend.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository for performing CRUD operations on Order entities.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByVendorId(Long vendorId);
    List<Order> findByCustomerId(Long customerId);
}
