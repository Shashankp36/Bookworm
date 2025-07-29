package com.example.service;

import com.example.model.Cart;
import java.util.List;
import java.util.Optional;

public interface ICartService {
    Cart saveCart(Cart cart);
    Optional<Cart> getCartById(int id);
    List<Cart> getAllCarts();
    void deleteCart(int id);
}
