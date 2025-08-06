package com.example.service;

import com.example.dto.AddCartItemRequest;
import com.example.model.Cart;
import com.example.model.CartItem;
import com.example.model.Discount;
import com.example.model.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


public interface ICartItemService {

    CartItem saveCartItem(CartItem cartItem);  // Add or Save

    Optional<CartItem> getCartItemById(int id);

    List<CartItem> getAllCartItems(); // Optional: for admin/debugging

    List<CartItem> getItemsByCartId(int cartId);

    void deleteCartItem(int productId, int cartId);

    void clearCart(int cartId);

  //  CartItem updateCartItemQuantity(int cartItemId, int newQuantity); // not required saakshi

    CartItem applyDiscountToCartItem(int cartItemId, Discount discount);

    Optional<CartItem> findItemByCartAndProduct(int cartId, int productId);
    
    //Saakshi- removed int quantity from addorUpdateItem

	CartItem addOrUpdateItem(Cart cart, AddCartItemRequest cartItemRequest, Product product);
}
