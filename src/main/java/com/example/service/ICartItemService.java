package com.example.service;

import com.example.model.CartItem;
import java.util.List;
import java.util.Optional;

public interface ICartItemService {
    CartItem saveCartItem(CartItem cartItem);
    Optional<CartItem> getCartItemById(int id);
    List<CartItem> getAllCartItems();
    void deleteCartItem(int id);
}
