package com.mahwi.backend.auth.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank(message = "Email or mobile number is required")
    @Pattern(
        regexp = "^([A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,})$|^(\\+?[0-9]{10,15})$",
        message = "Must be a valid email or mobile number"
    )
    private String identifier; // email or mobile

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String role = "CUSTOMER"; // defaults to CUSTOMER

    public RegisterRequest() {}
    
    public RegisterRequest(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }

    public String getIdentifier() { return identifier; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isEmail() {
        return identifier != null && identifier.contains("@");
    }

    public String getEmail() {
        return isEmail() ? identifier : null;
    }

    public String getMobile() {
        return !isEmail() ? identifier : null;
    }
}
