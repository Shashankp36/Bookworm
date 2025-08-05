package com.example.service;

import java.math.BigDecimal;

public interface IPaymentService {
    boolean processPayment(int userId, BigDecimal amount, String paymentMode);
}
