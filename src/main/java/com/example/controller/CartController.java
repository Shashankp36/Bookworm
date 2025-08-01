package com.example.controller;

import com.example.model.*;
import com.example.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    @Autowired
    private ICartItemService cartItemService;

    @Autowired

    private IUser userService;

    @Autowired
    private IProduct productService;


    @Autowired
    private IDiscountService discountService;


    // Ensure cart always exists for user
    @GetMapping("/user/{userId}/ensure")
    public ResponseEntity<Cart> ensureCartForUser(@PathVariable int userId) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) return ResponseEntity.badRequest().build();

        return cartService.getCartByUserId(userId)
                .or(() -> Optional.of(cartService.createCartForUser(userOpt.get())))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    // Get user's cart
    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCartByUser(@PathVariable int userId) {
        return cartService.getCartByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all items in a cart
    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable int cartId) {
        return ResponseEntity.ok(cartItemService.getItemsByCartId(cartId));
    }

    // Add item to cart (no quantity since eBooks/audiobooks)
    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItem> addItem(
            @PathVariable int cartId,
            @RequestParam int productId
    ) {
        Optional<Cart> cartOpt = cartService.getCartById(cartId);
        Optional<Product> productOpt = productService.getProductById(productId);

        if (cartOpt.isEmpty() || productOpt.isEmpty()) return ResponseEntity.badRequest().build();

        CartItem item = cartItemService.addOrUpdateItem(cartOpt.get(), productOpt.get());
        return ResponseEntity.ok(item);
    }

    // Remove item from cart by product ID
    @DeleteMapping("/{cartId}/items/product/{productId}")
    public ResponseEntity<Void> removeItemByProduct(
            @PathVariable int cartId,
            @PathVariable int productId
    ) {
        cartItemService.deleteCartItem(productId);
        return ResponseEntity.noContent().build();
    }

    // Apply discount
    @PutMapping("/items/{itemId}/discount")
    public ResponseEntity<CartItem> applyDiscount(
            @PathVariable int itemId,
            @RequestParam int discountId
    ) {
        Optional<Discount> discountOpt = discountService.getDiscountById(discountId);
        if (discountOpt.isEmpty()) return ResponseEntity.badRequest().build();

        CartItem item = cartItemService.applyDiscountToCartItem(itemId, discountOpt.get());
        if (item == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(item);
    }

    // Clear cart (typically after checkout/transaction)
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable int cartId) {
        cartItemService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    // Get total amount of the cart
    @GetMapping("/{cartId}/total")
    public ResponseEntity<BigDecimal> getCartTotal(@PathVariable int cartId) {
        Optional<Cart> cartOpt = cartService.getCartById(cartId);
        if (cartOpt.isEmpty()) return ResponseEntity.notFound().build();

        BigDecimal total = cartService.calculateCartTotal(cartOpt.get());
        return ResponseEntity.ok(total);
    }

    // Checkout endpoint (can be integrated with payment)
    @PostMapping("/{cartId}/checkout")
    public ResponseEntity<String> checkoutCart(@PathVariable int cartId) {
        // Placeholder for payment/checkout logic
        cartItemService.clearCart(cartId);  // After transaction, clear cart
        return ResponseEntity.ok("Checkout complete. Cart cleared.");
    }

    // List all carts (admin/debug)
    @GetMapping("/all")
    public ResponseEntity<List<Cart>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }
} 

