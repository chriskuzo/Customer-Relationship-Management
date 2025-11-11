package com.mahwi.backend.auth.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity representing system users (Admins, Staff, Customers, etc.)
 */
@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "mobile")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Display name or identifier (can be email or mobile) */
    @Column(nullable = false, unique = true)
    private String username;

    /** Optional email address */
    private String email;

    /** Optional mobile number (E.164 format recommended) */
    private String mobile;

    /** Hashed password */
    @Column(nullable = false)
    private String password;

    /** User roles (ADMIN, DAF, CUSTOMER, etc.) */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    /** Utility: check if the user has a specific role */
    public boolean hasRole(ERole role) {
        return roles.stream().anyMatch(r -> r.getName() == role);
    }
}
