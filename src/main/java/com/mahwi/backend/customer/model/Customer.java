package com.mahwi.backend.customer.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity representing a customer in the MAHWI platform.
 * Each customer is associated with a specific vendor and has a GPS location.
 */
@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String shopType;
    private String contact;
    private String tin;
    private Double latitude;
    private Double longitude;

    /**
     * Vendor association ensures each customer belongs to one vendor.
     */
    private Long vendorId;
}
