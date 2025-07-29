package com.example.service;

import com.example.model.Order;
import com.example.model.User;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	
	@Autowired
	public OrderService(OrderRepository orderRepository) {
	    this.orderRepository = orderRepository;
	}
	
	// Create or update an order
	public Order saveOrUpdateOrder(Order order) {
	    return orderRepository.save(order);
	}
	
	// Get all orders
	public List<Order> getAllOrders() {
	    return orderRepository.findAll();
	}
	
	// Get order by ID
	public Optional<Order> getOrderById(int id) {
	    return orderRepository.findById(id);
	}
	
	// Get orders by user
	public List<Order> getOrdersByUser(User user) {
	    return orderRepository.findByUser(user);
	}
	
	// Delete order by ID
	public void deleteOrder(int id) {
	    orderRepository.deleteById(id);
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
}