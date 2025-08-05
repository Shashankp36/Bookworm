package com.example.service;

import com.example.model.CartItem;
import com.example.model.Order;
import com.example.model.Purchase;
import com.example.model.Purchase.RoyaltyType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IPurchaseService {
    Purchase savePurchase(Purchase purchase);
    Optional<Purchase> getPurchaseById(int id);
    List<Purchase> getAllPurchases();
    void deletePurchase(int id);

    List<Purchase> getPurchasesByUser(int userId);
    List<Purchase> getPurchasesByProduct(int productId);
    List<Purchase> getPurchasesBetweenDates(LocalDateTime start, LocalDateTime end);
    List<Purchase> getPurchasesByRoyaltyType(RoyaltyType royaltyType);
    long countPurchasesByUser(int userId);
    Purchase save(Order order, CartItem item);
}
