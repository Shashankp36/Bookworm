package com.example.service;

import com.example.model.Discount;
import com.example.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {


	private final DiscountRepository discountRepository;

	@Autowired
	public DiscountService(DiscountRepository discountRepository) {
	    this.discountRepository = discountRepository;
	}
	
	// Create or Update
	public Discount saveOrUpdateDiscount(Discount discount) {
	    return discountRepository.save(discount);
	}
	
	// Get all
	public List<Discount> getAllDiscounts() {
	    return discountRepository.findAll();
	}
	
	// Get by ID
	public Optional<Discount> getDiscountById(int id) {
	    return discountRepository.findById(id);
	}
	
	// Delete by ID
	public void deleteDiscount(int id) {
	    discountRepository.deleteById(id);
	}
	
	// Get all discounts by Product ID
	public List<Discount> getDiscountsByProductId(int productId) {
	    return discountRepository.findByProduct_ProductId(productId);
	}
}
