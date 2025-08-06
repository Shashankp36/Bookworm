package com.example.service;

import com.example.model.User;
import com.example.model.Order;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final IUser userService ;
    
    @Autowired
    public OrderService(OrderRepository orderRepository, IUser userService) {
        this.orderRepository = orderRepository;
		this.userService = userService;
		
    }

    // Create or update an order
    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // Get all orders
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get order by ID
    @Override
    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    // Delete order by ID
    @Override
    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }

    // Get orders by user
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    // ✅ Get orders by user ID
    public List<Order> getOrdersByUserId(int userId) {
        return orderRepository.findByUser_UserId(userId);
    }

    // Update order status
    public boolean updateOrderStatus(int orderId, Order.OrderStatus status) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOrderStatus(status);
            orderRepository.save(order);
            return true;
        }
        return false;
    }
    @Override
    public Order createOrder(int userId, BigDecimal amount) {
        Order order = new Order();
        User user = userService.getUserById(userId).get();
        order.setUser(user);
        order.setTotalAmount(amount);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(Order.OrderStatus.completed);
        return orderRepository.save(order);
    }
}
