package com.example.service;

import com.example.model.RoyaltyPayment;
import com.example.model.RoyaltyPayment.PayeeType;
import com.example.model.RoyaltyPayment.RoyaltyType;
import com.example.repository.RoyaltyPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RoyaltyPaymentService {

@Autowired
private RoyaltyPaymentRepository royaltyPaymentRepository;

// Save a royalty payment
public RoyaltyPayment saveRoyaltyPayment(RoyaltyPayment royaltyPayment) {
    if (royaltyPayment.getPaymentDate() == null) {
        royaltyPayment.setPaymentDate(LocalDate.now());
    }
    return royaltyPaymentRepository.save(royaltyPayment);
}

// Get all royalty payments
public List<RoyaltyPayment> getAllRoyaltyPayments() {
    return royaltyPaymentRepository.findAll();
}

// Get royalty payment by ID
public Optional<RoyaltyPayment> getRoyaltyPaymentById(int id) {
    return royaltyPaymentRepository.findById(id);
}

// Delete royalty payment
public void deleteRoyaltyPayment(int id) {
    royaltyPaymentRepository.deleteById(id);
}

// Get royalty payments by Payee Type and ID
public List<RoyaltyPayment> getByPayee(PayeeType payeeType, int payeeId) {
    return royaltyPaymentRepository.findByPayeeTypeAndPayeeId(payeeType, payeeId);
}

// Get royalty payments by Product ID
public List<RoyaltyPayment> getByProductId(int productId) {
    return royaltyPaymentRepository.findByProductProductId(productId);
}

// Get royalty payments by Order ID
public List<RoyaltyPayment> getByOrderId(int orderId) {
    return royaltyPaymentRepository.findByOrderOrderId(orderId);
}

// Get total royalty amount paid to a payee
public BigDecimal getTotalRoyaltyAmount(PayeeType payeeType, int payeeId) {
    return royaltyPaymentRepository.sumAmountByPayeeTypeAndPayeeId(payeeType, payeeId)
            .orElse(BigDecimal.ZERO);
}

// Get royalty payments by royalty type (purchase/rental)
public List<RoyaltyPayment> getByRoyaltyType(RoyaltyType royaltyType) {
    return royaltyPaymentRepository.findByRoyaltyType(royaltyType);
}
}