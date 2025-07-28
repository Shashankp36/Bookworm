package com.example.service;

import com.example.model.Cart;
import com.example.model.User;
import com.example.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    // 1. Create a cart for a new user
    public Cart createCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCreatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    // 2. Get a user's cart by their user ID
    public Optional<Cart> getCartByUserId(int userId) {
        return cartRepository.findByUserUserId(userId);
    }

    // 3. Check if a user already has a cart
    public boolean doesCartExistForUser(int userId) {
        return cartRepository.findByUserUserId(userId).isPresent();
    }

    // 4. Get cart by cart ID
    public Optional<Cart> getCartById(int cartId) {
        return cartRepository.findById(cartId);
    }

    // 5. Save or update a cart
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    // 6. Delete a cart (e.g., after checkout or user deletion)
    public void deleteCartById(int cartId) {
        cartRepository.deleteById(cartId);
    }

    // 7. Delete cart by user ID (optional)
    public void deleteCartByUserId(int userId) {
        Optional<Cart> optionalCart = cartRepository.findByUserUserId(userId);
        optionalCart.ifPresent(cart -> cartRepository.deleteById(cart.getCartId()));
    }
}
