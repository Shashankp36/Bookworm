package com.example.service;

import com.example.model.Cart;
import com.example.model.User;

import java.util.List;
import java.util.Optional;

public interface ICartService {
Cart saveCart(Cart cart);
Optional<Cart> getCartById(int id);
List<Cart> getAllCarts();
void deleteCart(int id);


// Custom user-specific operations
Cart createCartForUser(User user);
Optional<Cart> getCartByUserId(int userId);
boolean doesCartExistForUser(int userId);
void deleteCartByUserId(int userId);
}