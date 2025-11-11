package com.mahwi.backend.order.controller;

import com.mahwi.backend.order.model.Order;
import com.mahwi.backend.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller providing order management endpoints.
 */
@RestController
@RequestMapping("/api/orders")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Orders", description = "Order management - DAF/ADMIN only")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Retrieves all orders for a vendor or customer.
     * Example: /api/orders?vendorId=1
     */
    @GetMapping
    @Operation(summary = "Get all orders")
    @PreAuthorize("hasAnyRole('DAF', 'ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    /**
     * Retrieves an order by its ID.
     * Example: /api/orders/5
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    @PreAuthorize("hasAnyRole('DAF', 'ADMIN')")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    /**
     * Creates a new order.
     */
    @PostMapping
    @Operation(summary = "Create new order")
    @PreAuthorize("hasAnyRole('DAF', 'ADMIN')")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    /**
     * Updates an existing order.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update order")
    @PreAuthorize("hasAnyRole('DAF', 'ADMIN')")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return ResponseEntity.ok(orderService.updateOrder(id, order));
    }

    /**
     * Deletes an order.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete order")
    @PreAuthorize("hasAnyRole('DAF', 'ADMIN')")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
