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

    // Create a new discount
    @PostMapping
    public ResponseEntity<Discount> createDiscount(@RequestBody Discount discount) {
        Discount savedDiscount = discountService.saveDiscount(discount);
        return ResponseEntity.ok(savedDiscount);
    }

    // Get all discounts
    @GetMapping
    public ResponseEntity<List<Discount>> getAllDiscounts() {
        return ResponseEntity.ok(discountService.getAllDiscounts());
    }

    // Get a discount by ID
    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable int id) {
        Optional<Discount> discount = discountService.getDiscountById(id);
        return discount.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a discount by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable int id) {
        Optional<Discount> discount = discountService.getDiscountById(id);
        if (discount.isPresent()) {
            discountService.deleteDiscount(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all discounts by Product ID
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Discount>> getDiscountsByProductId(@PathVariable int productId) {
        List<Discount> discounts = discountService.getDiscountsByProductId(productId);
        return ResponseEntity.ok(discounts);
    }

    // Update a discount
    @PutMapping("/{id}")
    public ResponseEntity<Discount> updateDiscount(@PathVariable int id, @RequestBody Discount discountDetails) {
        Optional<Discount> optionalDiscount = discountService.getDiscountById(id);
        if (optionalDiscount.isPresent()) {
            Discount discount = optionalDiscount.get();
            discount.setDiscountType(discountDetails.getDiscountType());
            discount.setValue(discountDetails.getValue());
            discount.setProduct(discountDetails.getProduct());
            Discount updatedDiscount = discountService.saveDiscount(discount);
            return ResponseEntity.ok(updatedDiscount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
