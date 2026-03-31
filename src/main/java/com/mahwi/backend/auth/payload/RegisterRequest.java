package com.mahwi.backend.auth.payload;

import com.mahwi.backend.auth.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    private String identifier;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100)
    private String password;
    
    @Email(message = "Email should be valid")
    private String email;
    
    private String role; // Optional: VENDOR, CUSTOMER, ADMIN
    
    // Getters and Setters
    public String getIdentifier() {
        return identifier;
    }
    
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    // Helper method to convert to User entity
    public User toUser() {
        User user = new User();
        user.setUsername(this.identifier);
        user.setPassword(this.password); // Important: set password here
        user.setEmail(this.email);
        return user;
    }
}
