package com.example.service;

import com.example.model.Order;
import com.example.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IOrderService {

    // Save or update order
    Order saveOrder(Order order);

    // Get order by ID
    Optional<Order> getOrderById(int id);

    // Get all orders
    List<Order> getAllOrders();

    // Delete order by ID
    void deleteOrder(int id);

    // Get orders by user
    List<Order> getOrdersByUser(User user);
    
    public List<Order> getOrdersByUserId(int userId);

    // Update order status
    boolean updateOrderStatus(int orderId, Order.OrderStatus status);

	Order createOrder(int userId, BigDecimal amount);
}
