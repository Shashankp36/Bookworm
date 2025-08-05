package com.example.service;

import com.example.model.Order;
import com.example.model.Transaction;
import com.example.model.Transaction.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ITransactionService {
    Transaction saveTransaction(Transaction transaction);
    Optional<Transaction> getTransactionById(int id);
    List<Transaction> getAllTransactions();
    void deleteTransaction(int id);

    List<Transaction> getTransactionsByUserId(int userId);
    List<Transaction> getTransactionsByOrderId(int orderId);
    List<Transaction> getTransactionsByStatus(PaymentStatus status);
    List<Transaction> getTransactionsByDateRange(LocalDateTime from, LocalDateTime to);
	void updateTransactionOrder(Transaction txn);
	Transaction createTransaction(int userId, Order order, BigDecimal amount, String paymentMode, boolean success);
}
