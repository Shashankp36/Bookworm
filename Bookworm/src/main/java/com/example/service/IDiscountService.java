package com.example.service;

import com.example.model.Discount;

import java.util.List;
import java.util.Optional;

public interface IDiscountService {
Discount saveDiscount(Discount discount);
Optional<Discount> getDiscountById(int id);
List<Discount> getAllDiscounts();
void deleteDiscount(int id);

List<Discount> getDiscountsByProductId(int productId);
}