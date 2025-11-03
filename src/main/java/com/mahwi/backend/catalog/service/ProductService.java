package com.mahwi.backend.catalog.service;

import com.mahwi.backend.catalog.model.Product;
import com.mahwi.backend.catalog.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for handling business logic for vendor products.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieves all products for a specific vendor.
     * @param vendorId vendor ID.
     * @return list of products.
     */
    public List<Product> getProductsByVendor(Long vendorId) {
        return productRepository.findByVendorId(vendorId);
    }

    /**
     * Adds a new product for a vendor.
     * Validates that no duplicate name exists for the same vendor.
     * @param product the product to create.
     * @return created product.
     */
    public Product addProduct(Product product) {
        if (productRepository.existsByNameAndVendorId(product.getName(), product.getVendorId())) {
            throw new RuntimeException("Product with the same name already exists for this vendor");
        }
        return productRepository.save(product);
    }

    /**
     * Updates an existing product.
     * @param id product ID.
     * @param updatedProduct new product details.
     * @return updated product entity.
     */
    public Product updateProduct(Long id, Product updatedProduct) {
        updatedProduct.setId(id);
        return productRepository.save(updatedProduct);
    }

    /**
     * Deletes a product by ID.
     * @param id product ID.
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
