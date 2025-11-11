package com.mahwi.backend.order.service;

import com.mahwi.backend.order.model.Order;
import com.mahwi.backend.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer handling business logic for orders.
 * Includes total amount calculation and status validation.
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Retrieves all orders for a vendor.
     * @param vendorId vendor ID.
     * @return list of orders.
     */
    public List<Order> getOrdersByVendor(Long vendorId) {
        return orderRepository.findByVendorId(vendorId);
    }

    /**
     * Retrieves all orders for a customer.
     * @param customerId customer ID.
     * @return list of orders.
     */
    public List<Order> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    /**
     * Creates a new order and automatically calculates total price.
     * @param order the order object to create.
     * @return saved order.
     */
    public Order createOrder(Order order) {
        order.setTotalAmount(order.getUnitPrice() * order.getQuantity());
        order.setStatus("NEW");
        return orderRepository.save(order);
    }

    /**
     * Updates an order’s status.
     * @param id order ID.
     * @param newStatus new status (PROCESSING, COMPLETED, CANCELLED).
     * @return updated order.
     */
    public Order updateOrderStatus(Long id, String newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!isValidStatusTransition(order.getStatus(), newStatus)) {
            throw new RuntimeException("Invalid status transition");
        }

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    /**
     * Validates allowed status transitions.
     */
    private boolean isValidStatusTransition(String current, String next) {
        return switch (current) {
            case "NEW" -> next.equals("PROCESSING") || next.equals("CANCELLED");
            case "PROCESSING" -> next.equals("COMPLETED") || next.equals("CANCELLED");
            case "COMPLETED", "CANCELLED" -> false;
            default -> false;
        };
    }

    /**
     * Deletes an order by ID.
     * @param id order ID.
     */
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    /**
     * Retrieves all orders.
     * @return list of all orders.
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Retrieves an order by its ID.
     * @param id order ID.
     * @return the order with the given ID.
     */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    /**
     * Updates an existing order.
     * @param id order ID.
     * @param order order object with updated fields.
     * @return updated order.
     */
    public Order updateOrder(Long id, Order order) {
        Order existing = getOrderById(id);
        // Update fields as needed
        return orderRepository.save(existing);
    }
}
