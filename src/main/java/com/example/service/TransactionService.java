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
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public Transaction saveTransaction(Transaction transaction) {
	    if (transaction.getPaymentDate() == null) {
	        transaction.setPaymentDate(LocalDateTime.now());
	    }
	    return transactionRepository.save(transaction);
	}
	
	public List<Transaction> getAllTransactions() {
	    return transactionRepository.findAll();
	}
	
	public Optional<Transaction> getTransactionById(int id) {
	    return transactionRepository.findById(id);
	}
	
	public void deleteTransaction(int id) {
	    transactionRepository.deleteById(id);
	}
	
	public List<Transaction> getTransactionsByUserId(int userId) {
	    return transactionRepository.findByUserUserId(userId);
	}
	
	public List<Transaction> getTransactionsByOrderId(int orderId) {
	    return transactionRepository.findByOrderOrderId(orderId);
	}
	
	public List<Transaction> getTransactionsByStatus(PaymentStatus status) {
	    return transactionRepository.findByPaymentStatus(status);
	}
	
	public List<Transaction> getTransactionsByDateRange(LocalDateTime from, LocalDateTime to) {
	    return transactionRepository.findByPaymentDateBetween(from, to);
	}
}