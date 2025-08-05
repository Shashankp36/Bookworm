package com.example.service;

import com.example.model.CartItem;
import com.example.model.Order;
import com.example.model.OrderDetail;
import com.example.model.Product;

import java.util.List;
import java.util.Optional;

public interface IOrderDetailService {

OrderDetail saveOrUpdateOrderDetail(OrderDetail orderDetail);

List<OrderDetail> getAllOrderDetails();

Optional<OrderDetail> getOrderDetailById(int id);

List<OrderDetail> getOrderDetailsByOrder(Order order);

List<OrderDetail> getOrderDetailsByProduct(Product product);

void deleteOrderDetailById(int id);

List<OrderDetail> getOrderDetailsByOrderId(int orderId);

void saveOrderDetails(Order order, List<CartItem> items);
}