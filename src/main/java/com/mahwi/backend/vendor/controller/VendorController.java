package com.mahwi.backend.vendor.controller;

import com.mahwi.backend.vendor.model.Vendor;
import com.mahwi.backend.vendor.service.VendorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing vendors.
 * Accessible primarily to SUPER_ADMIN users.
 */
@RestController
@RequestMapping("/api/vendors")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Vendors", description = "Vendor management - DAF/ADMIN only")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    /**
     * Retrieves all vendors.
     * @return list of vendors.
     */
    @GetMapping
    @Operation(summary = "Get all vendors")
    @PreAuthorize("hasAnyRole('DAF', 'ADMIN')")
    public ResponseEntity<List<Vendor>> getAllVendors() {
        return ResponseEntity.ok(vendorService.getAllVendors());
    }

    /**
     * Retrieves a vendor by ID.
     * @param id vendor ID.
     * @return vendor details.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get vendor by ID")
    @PreAuthorize("hasAnyRole('DAF', 'ADMIN')")
    public ResponseEntity<Vendor> getVendorById(@PathVariable Long id) {
        return ResponseEntity.ok(vendorService.getVendorById(id));
    }

    /**
     * Creates a new vendor.
     * @param vendor vendor details.
     * @return created vendor.
     */
    @PostMapping
    @Operation(summary = "Create new vendor")
    @PreAuthorize("hasAnyRole('DAF', 'ADMIN')")
    public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor) {
        return ResponseEntity.ok(vendorService.createVendor(vendor));
    }

    /**
     * Updates a vendor.
     * @param id vendor ID.
     * @param vendor updated vendor details.
     * @return updated vendor.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update vendor")
    @PreAuthorize("hasAnyRole('DAF', 'ADMIN')")
    public ResponseEntity<Vendor> updateVendor(@PathVariable Long id, @RequestBody Vendor vendor) {
        return ResponseEntity.ok(vendorService.updateVendor(id, vendor));
    }

    /**
     * Deletes a vendor by ID.
     * @param id vendor ID.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete vendor")
    @PreAuthorize("hasAnyRole('DAF', 'ADMIN')")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.noContent().build();
    }
}
