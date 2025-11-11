package com.mahwi.backend.catalog.controller;

import com.mahwi.backend.catalog.dto.ProductRequest;
import com.mahwi.backend.catalog.dto.ProductResponse;
import com.mahwi.backend.catalog.model.Product;
import com.mahwi.backend.catalog.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing vendor products in the catalog.
 */
@RestController
@RequestMapping("/api/catalog/products")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Catalog", description = "Product catalog management (DAF/ADMIN only)")
@Validated
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 🔹 Get all products (non-paginated)
    @GetMapping("/all")
    @Operation(summary = "Get all products (non-paginated)")
    @PreAuthorize("hasAnyRole('DAF','ADMIN')")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> result = productService.findAll()
                .stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // 🔹 Search / filter (paginated)
    @GetMapping
    @Operation(summary = "Search products (paginated)",
               description = "Optional filters: q (name contains), minPrice, maxPrice, active, vendorId")
    @PreAuthorize("hasAnyRole('DAF','ADMIN')")
    public ResponseEntity<Page<ProductResponse>> searchProducts(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Long vendorId,
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {

        Page<Product> page = productService.search(q, minPrice, maxPrice, active, vendorId, pageable);
        return ResponseEntity.ok(page.map(ProductResponse::fromEntity));
    }

    // 🔹 Get product by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    @PreAuthorize("hasAnyRole('DAF','ADMIN')")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(ProductResponse.fromEntity(product));
    }

    // 🔹 Create a new product
    @PostMapping
    @Operation(summary = "Create a new product")
    @PreAuthorize("hasAnyRole('DAF','ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        Product created = productService.create(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductResponse.fromEntity(created));
    }

    // 🔹 Update an existing product
    @PutMapping("/{id}")
    @Operation(summary = "Update product details")
    @PreAuthorize("hasAnyRole('DAF','ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        Product updated = productService.update(id, request.toEntity());
        return ResponseEntity.ok(ProductResponse.fromEntity(updated));
    }

    // 🔹 Delete a product
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product by ID")
    @PreAuthorize("hasAnyRole('DAF','ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
