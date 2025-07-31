package com.example.service;

import com.example.model.Transaction;
import com.example.model.Transaction.PaymentStatus;
import com.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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
}
