package com.example.service;

import com.example.model.Purchase;
import com.example.model.Purchase.RoyaltyType;
import com.example.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

@Autowired
private PurchaseRepository purchaseRepository;

	// Save a new purchase
	public Purchase savePurchase(Purchase purchase) {
	    if (purchase.getPurchaseDate() == null) {
	        purchase.setPurchaseDate(LocalDateTime.now());
	    }
	    return purchaseRepository.save(purchase);
	}
	
	// Get all purchases
	public List<Purchase> getAllPurchases() {
	    return purchaseRepository.findAll();
	}
	
	// Get purchase by ID
	public Optional<Purchase> getPurchaseById(int id) {
	    return purchaseRepository.findById(id);
	}
	
	// Delete purchase
	public void deletePurchase(int id) {
	    purchaseRepository.deleteById(id);
	}
	
	// Get purchases by user
	public List<Purchase> getPurchasesByUser(int userId) {
	    return purchaseRepository.findByUserUserId(userId);
	}
	
	// Get purchases by product
	public List<Purchase> getPurchasesByProduct(int productId) {
	    return purchaseRepository.findByProductProductId(productId);
	}
	
	// Get purchases within date range
	public List<Purchase> getPurchasesBetweenDates(LocalDateTime start, LocalDateTime end) {
	    return purchaseRepository.findByPurchaseDateBetween(start, end);
	}
	
	// Get purchases by royalty type
	public List<Purchase> getPurchasesByRoyaltyType(RoyaltyType royaltyType) {
	    return purchaseRepository.findByRoyaltyType(royaltyType);
	}
	
	// Count purchases by user
	public long countPurchasesByUser(int userId) {
	    return purchaseRepository.countByUserUserId(userId);
	}
}