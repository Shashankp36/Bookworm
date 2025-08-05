package com.example.service;

import com.example.model.CartItem;
import com.example.model.Order;
import com.example.model.Purchase;
import com.example.model.Purchase.RoyaltyType;
import com.example.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService implements IPurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    // Save a new purchase
    @Override
    public Purchase savePurchase(Purchase purchase) {
        if (purchase.getPurchaseDate() == null) {
            purchase.setPurchaseDate(LocalDateTime.now());
        }
        return purchaseRepository.save(purchase);
    }

    // Get all purchases
    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    // Get purchase by ID
    @Override
    public Optional<Purchase> getPurchaseById(int id) {
        return purchaseRepository.findById(id);
    }

    // Delete purchase
    @Override
    public void deletePurchase(int id) {
        purchaseRepository.deleteById(id);
    }

    // Get purchases by user
    @Override
    public List<Purchase> getPurchasesByUser(int userId) {
        return purchaseRepository.findByUserUserId(userId);
    }

    // Get purchases by product
    @Override
    public List<Purchase> getPurchasesByProduct(int productId) {
        return purchaseRepository.findByProductProductId(productId);
    }

    // Get purchases within date range
    @Override
    public List<Purchase> getPurchasesBetweenDates(LocalDateTime start, LocalDateTime end) {
        return purchaseRepository.findByPurchaseDateBetween(start, end);
    }

    // Get purchases by royalty type
    @Override
    public List<Purchase> getPurchasesByRoyaltyType(RoyaltyType royaltyType) {
        return purchaseRepository.findByRoyaltyType(royaltyType);
    }

    // Count purchases by user
    @Override
    public long countPurchasesByUser(int userId) {
        return purchaseRepository.countByUserUserId(userId);
    }
    @Override
    public Purchase save(Order order, CartItem item) {
        Purchase purchase = new Purchase();
        purchase.setOrder(order);
        purchase.setUser(order.getUser()); // Set the user from order
        purchase.setProduct(item.getProduct());

        // Set price paid
        purchase.setPricePaid(item.getProduct().getPrice());

        // Dummy royalty logic (replace later with real logic)
        purchase.setAuthorRoyalty(item.getProduct().getPrice().multiply(java.math.BigDecimal.valueOf(0.10)));
        purchase.setPublisherRoyalty(item.getProduct().getPrice().multiply(java.math.BigDecimal.valueOf(0.05)));
        purchase.setRoyaltyType(RoyaltyType.percentage);

        // Set date
        purchase.setPurchaseDate(LocalDateTime.now());

        return purchaseRepository.save(purchase);
    }


}
