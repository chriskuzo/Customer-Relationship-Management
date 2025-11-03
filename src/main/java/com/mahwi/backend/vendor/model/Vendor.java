package com.mahwi.backend.vendor.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Represents a vendor (company or supplier) registered in the MAHWI platform.
 * Vendors are managed by SUPER_ADMIN users.
 */
@Data
@Entity
@Table(name = "vendors")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String tin;
    private String industry;
    private String contactNumber;
    private String email;
    private String address;
    private String logoUrl;
}
