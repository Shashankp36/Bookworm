package com.example.service;

import com.example.model.Order;
import com.example.model.Transaction;
import com.example.model.Transaction.PaymentStatus;
import com.example.model.User;
import com.example.repository.TransactionRepository;
import com.example.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        if (transaction.getPaymentDate() == null) {
            transaction.setPaymentDate(LocalDateTime.now());
        }
        return transactionRepository.save(transaction);
    }

    @Override
    public Optional<Transaction> getTransactionById(int id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public void deleteTransaction(int id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public List<Transaction> getTransactionsByUserId(int userId) {
        return transactionRepository.findByUserUserId(userId);
    }

    @Override
    public List<Transaction> getTransactionsByOrderId(int orderId) {
        return transactionRepository.findByOrderOrderId(orderId);
    }

    @Override
    public List<Transaction> getTransactionsByStatus(PaymentStatus status) {
        return transactionRepository.findByPaymentStatus(status);
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(LocalDateTime from, LocalDateTime to) {
        return transactionRepository.findByPaymentDateBetween(from, to);
    }

    @Override
    public Transaction createTransaction(int userId, Order order, BigDecimal amount,
                                         String paymentMode, boolean success) {
        Transaction txn = new Transaction();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        txn.setUser(user);

        if (success && order != null) {
            txn.setOrder(order);
        } else {
            txn.setOrder(null); // no order reference on failure
        }

        txn.setAmount(amount);
        txn.setPaymentMode(paymentMode);
        txn.setPaymentStatus(success ? PaymentStatus.success : PaymentStatus.failed);
        txn.setPaymentDate(LocalDateTime.now());

        return transactionRepository.save(txn);
    }


    @Override
    public void updateTransactionOrder(Transaction txn) {
        transactionRepository.save(txn);
    }
}
