package com.mahwi.backend.catalog.service;

import com.mahwi.backend.catalog.model.Product;
import com.mahwi.backend.catalog.repository.ProductRepository;
import com.mahwi.backend.catalog.repository.ProductSpecifications;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.mahwi.backend.catalog.repository.ProductSpecifications.*;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 🔹 List all products (admin or internal use)
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // 🔹 List products by vendor
    @Transactional(readOnly = true)
    public List<Product> findByVendor(Long vendorId) {
        if (vendorId == null) throw new IllegalArgumentException("Vendor ID is required");
        return productRepository.findByVendorId(vendorId);
    }

    // 🔹 Find one by ID
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
    }

    // 🔹 Create new product
    public Product create(Product product) {
        validateProduct(product);
        if (productRepository.existsByNameAndVendorId(product.getName(), product.getVendorId())) {
            throw new IllegalArgumentException("A product with this name already exists for this vendor");
        }
        return productRepository.save(product);
    }

    // 🔹 Backward compatibility alias
    public Product addProduct(Product product) {
        return create(product);
    }

    // 🔹 Update existing product
    public Product update(Long id, Product updated) {
        Product existing = findById(id);

        boolean vendorChanged = !existing.getVendorId().equals(updated.getVendorId());
        boolean nameChanged = !existing.getName().equals(updated.getName());

        if ((vendorChanged || nameChanged)
                && productRepository.existsByNameAndVendorId(updated.getName(), updated.getVendorId())) {
            throw new IllegalArgumentException("Product with this name already exists for this vendor");
        }

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());
        existing.setActive(updated.isActive());
        existing.setVendorId(updated.getVendorId());

        return productRepository.save(existing);
    }

    // 🔹 Delete product
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }

    // 🔹 Advanced search using Specifications
    @Transactional(readOnly = true)
    public Page<Product> search(String q, BigDecimal minPrice, BigDecimal maxPrice,
                                Boolean active, Long vendorId, Pageable pageable) {

        Specification<Product> spec = Specification
                .where(nameContains(q))
                .and(minPrice(minPrice))
                .and(maxPrice(maxPrice))
                .and(ProductSpecifications.active(active))
                .and(ProductSpecifications.vendorIdEquals(vendorId));

        return productRepository.findAll(spec, pageable);
    }

    // 🔹 Private validation helper
    private void validateProduct(Product product) {
        if (product.getVendorId() == null)
            throw new IllegalArgumentException("Vendor ID must be specified");
        if (product.getPrice() != null && product.getPrice().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Price must be non-negative");
    }
}
