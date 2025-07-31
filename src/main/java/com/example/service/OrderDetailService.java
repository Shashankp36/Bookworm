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

    /**
     * Save or update an OrderDetail entity.
     */
    public OrderDetail saveOrUpdateOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    /**
     * Retrieve all OrderDetails.
     */
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    /**
     * Get a specific OrderDetail by its ID.
     */
    public Optional<OrderDetail> getOrderDetailById(int id) {
        return orderDetailRepository.findById(id);
    }

    /**
     * Get all OrderDetails associated with a specific Order.
     */
    public List<OrderDetail> getOrderDetailsByOrder(Order order) {
        return orderDetailRepository.findByOrder(order);
    }

    /**
     * Get all OrderDetails associated with a specific Product.
     */
    public List<OrderDetail> getOrderDetailsByProduct(Product product) {
        return orderDetailRepository.findByProduct(product);
    }

    /**
     * Delete an OrderDetail by its ID.
     */
    public void deleteOrderDetailById(int id) {
        orderDetailRepository.deleteById(id);
    }
}
