package com.example.service;

import com.example.model.OrderDetail;
import java.util.List;
import java.util.Optional;

public interface IOrderDetailService {
    OrderDetail saveOrderDetail(OrderDetail orderDetail);
    Optional<OrderDetail> getOrderDetailById(int id);
    List<OrderDetail> getAllOrderDetails();
    void deleteOrderDetail(int id);
}
