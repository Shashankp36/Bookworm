package com.example.service;

import com.example.model.Cart;
import com.example.model.CartItem;
import com.example.model.Discount;
import com.example.model.Product;
import com.example.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    // 1. Add a new item to the cart
    public CartItem addItemToCart(CartItem item) {
        return cartItemRepository.save(item);
    }

    // 2. Get all items in a specific cart
    public List<CartItem> getItemsByCartId(int cartId) {
        return cartItemRepository.findByCartCartId(cartId);
    }

    // 3. Get a specific cart item by ID
    public Optional<CartItem> getCartItemById(int cartItemId) {
        return cartItemRepository.findById(cartItemId);
    }

    // 4. Update quantity of an existing cart item
    public CartItem updateCartItemQuantity(int cartItemId, int newQuantity) {
        Optional<CartItem> optional = cartItemRepository.findById(cartItemId);
        if (optional.isPresent()) {
            CartItem item = optional.get();
            item.setQuantity(newQuantity);
            return cartItemRepository.save(item);
        }
        return null;
    }

    // 5. Apply or update discount on a cart item
    public CartItem applyDiscountToCartItem(int cartItemId, Discount discount) {
        Optional<CartItem> optional = cartItemRepository.findById(cartItemId);
        if (optional.isPresent()) {
            CartItem item = optional.get();
            item.setAppliedDiscount(discount);
            return cartItemRepository.save(item);
        }
        return null;
    }

    // 6. Remove an item from the cart
    public void removeCartItem(int cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    // 7. Remove all items from a cart
    public void clearCart(int cartId) {
        List<CartItem> items = cartItemRepository.findByCartCartId(cartId);
        cartItemRepository.deleteAll(items);
    }

    // 8. Check if a product is already in the cart
    public Optional<CartItem> findItemByCartAndProduct(int cartId, int productId) {
        return cartItemRepository.findByCartCartIdAndProductProductId(cartId, productId);
    }

    // 9. Update or add if exists
    public CartItem addOrUpdateItem(Cart cart, Product product, int quantity) {
        Optional<CartItem> existing = cartItemRepository.findByCartCartIdAndProductProductId(cart.getCartId(), product.getProductId());
        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + quantity);
            return cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            return cartItemRepository.save(newItem);
        }
    }
}
