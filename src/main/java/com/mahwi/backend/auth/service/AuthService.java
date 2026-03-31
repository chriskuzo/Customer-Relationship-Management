package com.mahwi.backend.auth.service;

import com.mahwi.backend.auth.model.ERole;
import com.mahwi.backend.auth.model.Role;
import com.mahwi.backend.auth.model.User;
import com.mahwi.backend.auth.repository.RoleRepository;
import com.mahwi.backend.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Service responsible for handling registration and login logic.
 */
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Registers a new user by encoding their password and assigning a role.
     *
     * @param user the user entity containing registration details
     * @param roleStr the role string (e.g., "ADMIN", "DAF", "SALES", "CUSTOMER")
     * @return the saved user entity
     */
    public User register(User user, String roleStr) {
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Determine role (default to CUSTOMER if not specified)
        String roleName = (roleStr != null && !roleStr.isEmpty()) 
            ? "ROLE_" + roleStr.toUpperCase() 
            : "ROLE_CUSTOMER";

        // Convert string to ERole enum
        ERole eRole;
        try {
            eRole = ERole.valueOf(roleName);
        } catch (IllegalArgumentException e) {
            eRole = ERole.ROLE_CUSTOMER; // fallback to customer
        }

        // Find role in database - USE eRole, not hardcoded ROLE_USER
        final ERole finalERole = eRole;

        Role role = roleRepository.findByName(finalERole.name())
                .orElseThrow(() -> new RuntimeException("Role not found: " + finalERole));

        // Assign role to user
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        // Save and return
        return userRepository.save(user);
    }

    /**
     * Authenticates a user and returns a JWT token if credentials are valid.
     *
     * @param identifier the user's email or mobile number
     * @param rawPassword the plain password to verify
     * @return a signed JWT token if valid
     */
    public String login(String identifier, String rawPassword) {
        User user = userRepository.findByEmail(identifier)
                .or(() -> userRepository.findByMobile(identifier))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtService.generateToken(user);
    }
}
