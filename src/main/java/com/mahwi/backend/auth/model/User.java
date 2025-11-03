package com.mahwi.backend.auth.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity representing system users such as Admins, Vendors, and Sales personnel.
 */
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String email;

    @Column(nullable = false)
    private String password;

    private Long vendorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
}
