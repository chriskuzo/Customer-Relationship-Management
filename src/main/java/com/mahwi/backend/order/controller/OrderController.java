package com.mahwi.backend.order.controller;

import com.mahwi.backend.order.model.Order;
import com.mahwi.backend.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller providing order management endpoints.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Retrieves all orders for a vendor or customer.
     * Example: /api/orders?vendorId=1
     */
    @GetMapping
    public ResponseEntity<List<Order>> getOrders(@RequestParam(required = false) Long vendorId,
                                                 @RequestParam(required = false) Long customerId) {
        if (vendorId != null) return ResponseEntity.ok(orderService.getOrdersByVendor(vendorId));
        if (customerId != null) return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
        return ResponseEntity.badRequest().build();
    }

    /**
     * Creates a new order.
     */
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    /**
     * Updates an order’s status.
     * Example: PATCH /api/orders/5/status?value=COMPLETED
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id,
                                                   @RequestParam String value) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, value));
    }

    /**
     * Deletes an order.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
