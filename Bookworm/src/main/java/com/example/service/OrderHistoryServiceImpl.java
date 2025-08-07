package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.OrderItemHistoryDTO;
import com.example.model.Purchase;
import com.example.model.Rental;
import com.example.repository.PurchaseRepository;
import com.example.repository.RentalRepository;

@Service
public class OrderHistoryServiceImpl implements IOrderHistoryService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Override
    public List<OrderItemHistoryDTO> getOrderHistoryByUserId(int userId) {
        List<OrderItemHistoryDTO> history = new ArrayList<>();

        List<Purchase> purchases = purchaseRepository.findByUserUserId(userId);
        for (Purchase p : purchases) {
            history.add(new OrderItemHistoryDTO(
                p.getOrder().getOrderId(),
                p.getProduct().getProductId(),
                "purchase",
                p.getPricePaid(),
                p.getPurchaseDate(),
                null,
                null
            ));
        }

        List<Rental> rentals = rentalRepository.findByUserUserId(userId);
        for (Rental r : rentals) {
            history.add(new OrderItemHistoryDTO(
                r.getOrder() != null ? r.getOrder().getOrderId() : 0,
                r.getProduct().getProductId(),
                "rental",
                r.getPricePaid(),
                null,
                r.getRentalStart(),
                r.getRentalEnd()
            ));
        }

        return history;
    }
}
