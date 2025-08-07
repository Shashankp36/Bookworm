package com.example.service;

import java.util.List;

import com.example.dto.OrderItemHistoryDTO;

public interface IOrderHistoryService {
    List<OrderItemHistoryDTO> getOrderHistoryByUserId(int userId);
}

