package com.example.service;

import com.example.model.Purchase;
import java.util.List;
import java.util.Optional;

public interface IPurchaseService {
    Purchase savePurchase(Purchase purchase);
    Optional<Purchase> getPurchaseById(int id);
    List<Purchase> getAllPurchases();
    void deletePurchase(int id);
}
