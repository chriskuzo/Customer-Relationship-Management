package com.mahwi.backend.customer.controller;

import com.mahwi.backend.customer.model.Customer;
import com.mahwi.backend.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller providing endpoints for managing customers.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Retrieves all customers or those under a specific vendor.
     * Example: /api/customers?vendorId=1
     */
    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(@RequestParam(required = false) Long vendorId) {
        return ResponseEntity.ok(customerService.getCustomers(vendorId));
    }

    /**
     * Registers a new customer with GPS coordinates.
     */
    @PostMapping
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.registerCustomer(customer));
    }

    /**
     * Updates an existing customer.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customer));
    }

    /**
     * Deletes a customer record.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
