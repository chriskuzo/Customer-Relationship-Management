package com.mahwi.backend.catalog.controller;

import com.mahwi.backend.catalog.model.Product;
import com.mahwi.backend.catalog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing vendor products.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Retrieves products for a given vendor.
     * Example: /api/products?vendorId=2
     */
    @GetMapping
    public ResponseEntity<List<Product>> getProductsByVendor(@RequestParam Long vendorId) {
        return ResponseEntity.ok(productService.getProductsByVendor(vendorId));
    }

    /**
     * Adds a new product to a vendor's catalog.
     */
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.addProduct(product));
    }

    /**
     * Updates an existing product.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    /**
     * Deletes a product.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
