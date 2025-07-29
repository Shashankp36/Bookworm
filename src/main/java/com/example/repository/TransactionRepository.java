package com.example.repository;

import com.example.model.Transaction;
import com.example.model.Transaction.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	List<Transaction> findByUserUserId(int userId);
	
	List<Transaction> findByOrderOrderId(int orderId);
	
	List<Transaction> findByPaymentStatus(PaymentStatus status);
	
	List<Transaction> findByPaymentDateBetween(LocalDateTime start, LocalDateTime end);
}