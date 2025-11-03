package com.mahwi.backend.customer.service;

import com.mahwi.backend.customer.model.Customer;
import com.mahwi.backend.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer containing business logic for managing customers.
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Retrieves all customers, or filters them by vendor ID.
     * @param vendorId optional vendor ID for filtering.
     * @return list of customers.
     */
    public List<Customer> getCustomers(Long vendorId) {
        if (vendorId != null) {
            return customerRepository.findByVendorId(vendorId);
        }
        return customerRepository.findAll();
    }

    /**
     * Registers a new customer for a vendor.
     * Validates GPS coordinates.
     * @param customer the customer entity to create.
     * @return saved customer.
     */
    public Customer registerCustomer(Customer customer) {
        if (customer.getLatitude() == null || customer.getLongitude() == null) {
            throw new RuntimeException("Customer GPS location is required");
        }
        return customerRepository.save(customer);
    }

    /**
     * Updates an existing customer by ID.
     * @param id the customer ID.
     * @param updatedCustomer new data for the customer.
     * @return updated customer entity.
     */
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        updatedCustomer.setId(id);
        return customerRepository.save(updatedCustomer);
    }

    /**
     * Deletes a customer record.
     * @param id customer ID.
     */
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
