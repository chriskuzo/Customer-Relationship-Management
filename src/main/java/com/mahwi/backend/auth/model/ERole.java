package com.mahwi.backend.auth.model;

public enum ERole {
    ROLE_USER,     // Limited access for regular users
    ROLE_ADMIN,    // Full system access
    ROLE_DAF,      // Finance and inventory access
    ROLE_SALES,    // Customer and sales access
    ROLE_CUSTOMER  // Shop and public access
}
