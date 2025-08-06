package com.example.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.configuration.SessionUserProvider;
import com.example.model.CartItem;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.Purchase;


import com.example.model.Shelf;
import com.example.model.ShelfItem;
import com.example.model.ShelfItem.AccessType;
import com.example.repository.PurchaseRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class PurchaseService implements IPurchaseService {

    private final ShelfItemService shelfItemService_1;

    private final PurchaseRepository purchaseRepository;
    private final SessionUserProvider provider;
    private final IShelf shelf;
    private final IShelfItemService shelfItemService;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, SessionUserProvider provider, IShelfItemService shelfItemService, IShelf shelf, ShelfItemService shelfItemService_1) {
        this.purchaseRepository = purchaseRepository;
		this.provider = provider;
		this.shelf = shelf;
		this.shelfItemService = shelfItemService;
		this.shelfItemService_1 = shelfItemService_1;
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

    // Count purchases by user
    @Override
    public long countPurchasesByUser(int userId) {
        return purchaseRepository.countByUserUserId(userId);
    }

    // Save purchase with actual royalty calculation from product
    @Override
    public Purchase save(Order order, CartItem item) {

        Product product = item.getProduct();
        BigDecimal price = product.getPrice();

        // Real royalty calculation (percentage-based)
        BigDecimal authorRoyalty = price.multiply(product.getRoyaltyAuthor().divide(BigDecimal.valueOf(100)));
        BigDecimal publisherRoyalty = price.multiply(product.getRoyaltyPublisher().divide(BigDecimal.valueOf(100)));

        Purchase purchase = new Purchase();
        purchase.setOrder(order);
        purchase.setUser(order.getUser());
        purchase.setProduct(product);
        purchase.setPricePaid(product.getDiscountedPrice());
        purchase.setAuthorRoyalty(authorRoyalty);
        purchase.setPublisherRoyalty(publisherRoyalty);
        purchase.setPurchaseDate(LocalDateTime.now());
        Purchase savePurchase = purchaseRepository.save(purchase);
        
        
        int userId =  savePurchase.getUser().getUserId();
        Shelf shelf1 = shelf.getShelfByUserId(userId).get();
        ShelfItem shelfItem = new ShelfItem();
        shelfItem.setPurchase(savePurchase);
        shelfItem.setFormat(product.getFormat());
        shelfItem.setShelf(shelf1);
        shelfItem.setAccessType(AccessType.purchase);
        shelfItemService.saveShelfItem(shelfItem);
        return savePurchase;
    }
}
