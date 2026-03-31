package com.mahwi.backend.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
        @UniqueConstraint(name = "uk_users_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_users_mobile", columnNames = "mobile")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String username;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    /** Optional mobile number (9–12 digits) */
    @Pattern(regexp = "^[0-9]{9,12}$", message = "Mobile must be 9 to 12 digits")
    @Column(nullable = true, unique = true)
    private String mobile;

    /** Hashed password */
    @NotBlank
    @Column(nullable = false)
    private String password;

    /** User roles (ADMIN, DAF, CUSTOMER, etc.) */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    /** Utility: check if the user has a specific role */
    public boolean hasRole(ERole role) {
        return roles.stream().anyMatch(r -> r.getName() == role);
    }
}
