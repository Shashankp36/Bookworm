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
public class CartItemService implements ICartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartItem saveCartItem(CartItem item) {
        return cartItemRepository.save(item);
    }

    @Override
    public Optional<CartItem> getCartItemById(int cartItemId) {
        return cartItemRepository.findById(cartItemId);
    }

    @Override
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    @Override
    public List<CartItem> getItemsByCartId(int cartId) {
        return cartItemRepository.findByCartCartId(cartId);
    }

    @Override
    public void deleteCartItem(int cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void clearCart(int cartId) {
        List<CartItem> items = cartItemRepository.findByCartCartId(cartId);
        cartItemRepository.deleteAll(items);
    }
//Saakshi - not required
//    @Override
//    public CartItem updateCartItemQuantity(int cartItemId, int newQuantity) {
//        Optional<CartItem> optional = cartItemRepository.findById(cartItemId);
//        if (optional.isPresent()) {
//            CartItem item = optional.get();
//            item.setQuantity(newQuantity);
//            return cartItemRepository.save(item);
//        }
//        return null;
//    }

    @Override
    public CartItem applyDiscountToCartItem(int cartItemId, Discount discount) {
        Optional<CartItem> optional = cartItemRepository.findById(cartItemId);
        if (optional.isPresent()) {
            CartItem item = optional.get();
            item.setAppliedDiscount(discount);
            return cartItemRepository.save(item);
        }
        return null;
    }

    @Override
    public Optional<CartItem> findItemByCartAndProduct(int cartId, int productId) {
        return cartItemRepository.findByCartCartIdAndProductProductId(cartId, productId);
    }
// Saakshi - removed quantity
    @Override
    public CartItem addOrUpdateItem(Cart cart, Product product) {
        Optional<CartItem> existing = cartItemRepository.findByCartCartIdAndProductProductId(cart.getCartId(), product.getProductId());
        if (existing.isPresent()) {
            CartItem item = existing.get();
            return cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            return cartItemRepository.save(newItem);
        }
    }
}
