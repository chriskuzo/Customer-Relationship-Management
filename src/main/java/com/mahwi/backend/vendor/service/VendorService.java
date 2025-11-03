package com.mahwi.backend.vendor.service;

import com.mahwi.backend.vendor.model.Vendor;
import com.mahwi.backend.vendor.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for business logic related to vendor management.
 * Includes validation, creation, and updating of vendor data.
 */
@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    /**
     * Retrieves all vendors in the system.
     * @return list of vendors.
     */
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    /**
     * Creates a new vendor if the name is unique.
     * @param vendor vendor entity to create.
     * @return created vendor entity.
     */
    public Vendor createVendor(Vendor vendor) {
        if (vendorRepository.existsByName(vendor.getName())) {
            throw new RuntimeException("Vendor with the same name already exists");
        }
        return vendorRepository.save(vendor);
    }

    /**
     * Updates an existing vendor by ID.
     * @param id vendor ID.
     * @param vendor updated vendor data.
     * @return updated vendor entity.
     */
    public Vendor updateVendor(Long id, Vendor vendor) {
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    /**
     * Deletes a vendor by ID.
     * @param id vendor ID.
     */
    public void deleteVendor(Long id) {
        vendorRepository.deleteById(id);
    }
}
