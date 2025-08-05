package com.example.service;

import java.math.BigDecimal;

public class PaymentService implements IPaymentService {

    @Override
    public boolean processPayment(int userId, BigDecimal amount, String paymentMode) {
        // ⚠️ Replace this with actual payment gateway logic later (Razorpay, Stripe, etc.)
        return true; // For now, always successful
    }
}