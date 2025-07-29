package com.example.service;

import com.example.model.Order;
import java.util.List;
import java.util.Optional;

public interface IOrderService {
    Order saveOrder(Order order);
    Optional<Order> getOrderById(int id);
    List<Order> getAllOrders();
    void deleteOrder(int id);
}
