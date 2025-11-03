package com.mahwi.backend.order.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Entity representing an order in the MAHWI platform.
 * Each order belongs to a vendor and a customer, and includes
 * product references, total amount, and status tracking.
 */
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long vendorId;
    private Long customerId;

    private String productName;
    private Integer quantity;
    private Double unitPrice;
    private Double totalAmount;

    /**
     * Possible statuses: NEW, PROCESSING, COMPLETED, CANCELLED.
     */
    private String status = "NEW";

    private LocalDateTime orderDate = LocalDateTime.now();
}
