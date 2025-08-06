package com.example.controller;

import com.example.model.Discount;
import com.example.service.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    private final IDiscountService discountService;

    @Autowired
    public DiscountController(IDiscountService discountService) {
        this.discountService = discountService;
    }

    // 1. Get all discounts (optional: for user display or debugging)
    @GetMapping
    public ResponseEntity<List<Discount>> getAllDiscounts() {
        List<Discount> discounts = discountService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    // 2. Get a discount by ID (used when applying a specific discount to a cart item)
    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable int id) {
        Optional<Discount> discount = discountService.getDiscountById(id);
        return discount.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    // 3. Get all discounts available for a specific product



    //  Admin routes
    
    // Create a new discount
    @PostMapping
    public ResponseEntity<Discount> createDiscount(@RequestBody Discount discount) {
        Discount savedDiscount = discountService.saveDiscount(discount);
        return ResponseEntity.ok(savedDiscount);
    }

    // Update discount
    @PutMapping("/{id}")
    public ResponseEntity<Discount> updateDiscount(@PathVariable int id, @RequestBody Discount updated) {
        Optional<Discount> existing = discountService.getDiscountById(id);
        if (existing.isEmpty()) return ResponseEntity.notFound().build();

        Discount discount = existing.get();
        discount.setDiscountType(updated.getDiscountType());
        discount.setValue(updated.getValue());
       
        return ResponseEntity.ok(discountService.saveDiscount(discount));
    }

    // Delete discount
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable int id) {
        if (discountService.getDiscountById(id).isEmpty())
            return ResponseEntity.notFound().build();

        discountService.deleteDiscount(id);
        return ResponseEntity.noContent().build();
    }
    
}
