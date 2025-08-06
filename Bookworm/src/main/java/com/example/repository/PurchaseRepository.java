package com.example.repository;

import com.example.model.Purchase;
import com.example.model.User;
import com.example.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
	List<Purchase> findByUserUserId(int userId);

	List<Purchase> findByProductProductId(int productId);
	

	
	List<Purchase> findByPurchaseDateBetween(LocalDateTime start, LocalDateTime end);
	
	long countByUserUserId(int userId);
}