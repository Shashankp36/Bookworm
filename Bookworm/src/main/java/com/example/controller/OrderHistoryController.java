package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.configuration.SessionUserProvider;
import com.example.dto.OrderItemHistoryDTO;
import com.example.service.IOrderHistoryService;

@RestController
@RequestMapping("/api/history")
public class OrderHistoryController {

    @Autowired
    private IOrderHistoryService orderHistoryService;
    
    @Autowired
    private SessionUserProvider provider;

    @GetMapping("/user")
    public ResponseEntity<List<OrderItemHistoryDTO>> getUserOrderHistory() {
    	
    	int userId=provider.getCurrentUser().get().getUserId();
        List<OrderItemHistoryDTO> history = orderHistoryService.getOrderHistoryByUserId(userId);
        return ResponseEntity.ok(history);
        
    }
}
