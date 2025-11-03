package com.mahwi.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the MAHWI Sales Platform backend application.
 * <p>
 * Runs the Spring Boot context and initializes all modules:
 * Authentication, Vendor, Customer, Product, and Order management.
 */
@SpringBootApplication
public class MahwiBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(MahwiBackendApplication.class, args);
    }
}
