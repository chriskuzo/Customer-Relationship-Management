package com.mahwi.backend.customer.controller;

import com.mahwi.backend.customer.model.Customer;
import com.mahwi.backend.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller providing endpoints for managing customers.
 */
@RestController
@RequestMapping("/api/customers")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Customers", description = "Customer management - SALES/ADMIN only")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Retrieves all customers or those under a specific vendor.
     * Example: /api/customers?vendorId=1
     */
    @GetMapping
    @Operation(summary = "Get all customers")
    @PreAuthorize("hasAnyRole('SALES', 'ADMIN')")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    /**
     * Retrieves a customer by their ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID")
    @PreAuthorize("hasAnyRole('SALES', 'ADMIN')")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    /**
     * Registers a new customer with GPS coordinates.
     */
    @PostMapping
    @Operation(summary = "Create new customer")
    @PreAuthorize("hasAnyRole('SALES', 'ADMIN')")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }

    /**
     * Updates an existing customer.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update customer")
    @PreAuthorize("hasAnyRole('SALES', 'ADMIN')")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customer));
    }

    /**
     * Deletes a customer record.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer")
    @PreAuthorize("hasAnyRole('SALES', 'ADMIN')")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
