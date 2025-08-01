package com.example.service;

import com.example.model.Cart;
import com.example.model.CartItem;
import com.example.model.Discount;
import com.example.model.ItemType;
import com.example.model.User;
import com.example.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {


@Autowired
private CartRepository cartRepository;

// 1. Create a cart for a new user
@Override
public Cart createCartForUser(User user) {
    Cart cart = new Cart();
    cart.setUser(user);
    cart.setCreatedAt(LocalDateTime.now());
    return cartRepository.save(cart);
}

// 2. Get a user's cart by their user ID
@Override
public Optional<Cart> getCartByUserId(int userId) {
    return cartRepository.findByUserUserId(userId);
}

// 3. Check if a user already has a cart
@Override
public boolean doesCartExistForUser(int userId) {
    return cartRepository.findByUserUserId(userId).isPresent();
}

// 4. Get cart by cart ID
@Override
public Optional<Cart> getCartById(int cartId) {
    return cartRepository.findById(cartId);
}

// 5. Save or update a cart
@Override
public Cart saveCart(Cart cart) {
    return cartRepository.save(cart);
}

// 6. Get all carts
@Override
public List<Cart> getAllCarts() {
    return cartRepository.findAll();
}

// 7. Delete a cart by cart ID
@Override
public void deleteCart(int id) {
    cartRepository.deleteById(id);
}

// 8. Delete a cart by user ID
@Override
public void deleteCartByUserId(int userId) {
    Optional<Cart> optionalCart = cartRepository.findByUserUserId(userId);
    optionalCart.ifPresent(cart -> cartRepository.deleteById(cart.getCartId()));
    
}
//Saakshi
//9. Total Cart Amount by cart ID.
@Override
public BigDecimal calculateCartTotal(Cart cart) {
    BigDecimal total = BigDecimal.ZERO;

    for (CartItem item : cart.getCartItems()) {
        BigDecimal price = item.getProduct().getPrice();
        Discount discount = item.getAppliedDiscount();
        BigDecimal finalPrice;

        // Determine base price based on type
        if (item.getItemType() == ItemType.RENT) {
            // 20% of full price for 7-day rental
            finalPrice = price.multiply(BigDecimal.valueOf(0.2));
        } else {
            // PURCHASE: start with full price
            finalPrice = price;
        }

        // Apply discount only for PURCHASE items (optional rule)
        if (item.getItemType() == ItemType.PURCHASE && discount != null && discount.getDiscountType() != null) {
            switch (discount.getDiscountType()) {
                case flat:
                    finalPrice = finalPrice.subtract(discount.getValue());
                    break;
                case percentage:
                    BigDecimal discountAmount = finalPrice.multiply(discount.getValue()).divide(BigDecimal.valueOf(100));
                    finalPrice = finalPrice.subtract(discountAmount);
                    break;
            }
        }

        // No negative totals
        if (finalPrice.compareTo(BigDecimal.ZERO) < 0)
            finalPrice = BigDecimal.ZERO;

        total = total.add(finalPrice);
    }

    return total;
}

}