package com.example.service;

import com.example.model.RoyaltyPayment;
import java.util.List;
import java.util.Optional;

public interface IRoyaltyPaymentService {
    RoyaltyPayment saveRoyaltyPayment(RoyaltyPayment royaltyPayment);
    Optional<RoyaltyPayment> getRoyaltyPaymentById(int id);
    List<RoyaltyPayment> getAllRoyaltyPayments();
    void deleteRoyaltyPayment(int id);
}
