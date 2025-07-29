package com.example.service;

import com.example.model.Order;
import com.example.model.OrderDetail;
import com.example.model.Product;
import com.example.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    // Create or update an OrderDetail
    public OrderDetail saveOrUpdateOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    // Get all OrderDetails
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    // Get OrderDetail by ID
    public Optional<OrderDetail> getOrderDetailById(int id) {
        return orderDetailRepository.findById(id);
    }

    // Get OrderDetails by Order
    public List<OrderDetail> getOrderDetailsByOrder(Order order) {
        return orderDetailRepository.findByOrder(order);
    }

    // Get OrderDetails by Product
    public List<OrderDetail> getOrderDetailsByProduct(Product product) {
        return orderDetailRepository.findByProduct(product);
    }

    // Delete OrderDetail by ID
    public void deleteOrderDetailById(int id) {
        orderDetailRepository.deleteById(id);
    }
}
