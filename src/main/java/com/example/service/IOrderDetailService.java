package com.example.service;

import com.example.model.Order;
import com.example.model.OrderDetail;
import com.example.model.Product;

import java.util.List;
import java.util.Optional;

public interface IOrderDetailService {


/**
 * Save or update an OrderDetail.
 */
OrderDetail saveOrUpdateOrderDetail(OrderDetail orderDetail);

/**
 * Get all OrderDetails.
 */
List<OrderDetail> getAllOrderDetails();

/**
 * Get an OrderDetail by ID.
 */
Optional<OrderDetail> getOrderDetailById(int id);

/**
 * Get OrderDetails by Order.
 */
List<OrderDetail> getOrderDetailsByOrder(Order order);

/**
 * Get OrderDetails by Product.
 */
List<OrderDetail> getOrderDetailsByProduct(Product product);

/**
 * Delete an OrderDetail by ID.
 */
void deleteOrderDetailById(int id);
}