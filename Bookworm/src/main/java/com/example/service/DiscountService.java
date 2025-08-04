package com.example.service;

import com.example.model.Discount;
import com.example.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountService implements IDiscountService {


private final DiscountRepository discountRepository;

@Autowired
public DiscountService(DiscountRepository discountRepository) {
    this.discountRepository = discountRepository;
}

@Override
public Discount saveDiscount(Discount discount) {
    return discountRepository.save(discount);
}

@Override
public List<Discount> getAllDiscounts() {
    return discountRepository.findAll();
}

@Override
public Optional<Discount> getDiscountById(int id) {
    return discountRepository.findById(id);
}

@Override
public void deleteDiscount(int id) {
    discountRepository.deleteById(id);
}

@Override
public List<Discount> getDiscountsByProductId(int productId) {
    return discountRepository.findByProduct_ProductId(productId);
}
}