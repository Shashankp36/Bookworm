package com.example.controller;

import com.example.configuration.SessionUserProvider;
import com.example.model.*;
import com.example.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart/")
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
    
    @Autowired
    private SessionUserProvider provider;

    
    // Ensure cart always exists for user
    @GetMapping("/user/ensure")
    public ResponseEntity<Cart> ensureCartForUser() {
    	int userId  = provider.getCurrentUser().get().getUserId();
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) return ResponseEntity.badRequest().build();

        return cartService.getCartByUserId(userId)
                .or(() -> Optional.of(cartService.createCartForUser(userOpt.get())))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    // Get user's cart
    @GetMapping("/user")
    public ResponseEntity<Cart> getCartByUser() {
    	int userId  = provider.getCurrentUser().get().getUserId();
        return cartService.getCartByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all items in a cart
    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCartItems() {
    	int userId = provider.getCurrentUser().get().getUserId();
    	Cart cart  = cartService.getCartByUserId(userId).get();
    	System.out.println(cart);
        return ResponseEntity.ok(cartItemService.getItemsByCartId(cart.getCartId()));
    }

    // Add item to cart (no quantity since eBooks/audiobooks)
    @PostMapping("/items")
    public ResponseEntity<CartItem> addItem(
            @RequestParam int productId
    ) {
    	int userId = provider.getCurrentUser().get().getUserId();
    	Optional<Cart> cartOpt  = cartService.getCartByUserId(userId);
        Optional<Product> productOpt = productService.getProductById(productId);

        if (cartOpt.isEmpty() || productOpt.isEmpty()) return ResponseEntity.badRequest().build();

        CartItem item = cartItemService.addOrUpdateItem(cartOpt.get(), productOpt.get());
        return ResponseEntity.ok(item);
    }

    // Remove item from cart by product ID
    @DeleteMapping("/items/product/{productId}")
    public ResponseEntity<Void> removeItemByProduct(
            @PathVariable int productId
    ) {
    	  int userId = provider.getCurrentUser().get().getUserId();
    	  Cart cart = cartService.getCartByUserId(userId).get();
    	     cartItemService.deleteCartItem(productId , cart.getCartId());
        return ResponseEntity.noContent().build();
    }

//    // Apply discount
//    @PutMapping("/items/discount")
//    public ResponseEntity<CartItem> applyDiscount(
//            @RequestParam int discountId
//    ) {
//        Optional<Discount> discountOpt = discountService.getDiscountById(discountId);
//        if (discountOpt.isEmpty()) return ResponseEntity.badRequest().build();
//
//        CartItem item = cartItemService.applyDiscountToCartItem(itemId, discountOpt.get());
//        if (item == null) return ResponseEntity.notFound().build();
//        return ResponseEntity.ok(item);
//    }

    // Clear cart (typically after checkout/transaction)
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
    	int userId = provider.getCurrentUser().get().getUserId();
  	  Cart cart = cartService.getCartByUserId(userId).get();
        cartItemService.clearCart(cart.getCartId());
        return ResponseEntity.noContent().build();
    }

    // Get total amount of the cart
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getCartTotal() {
    	int userId = provider.getCurrentUser().get().getUserId();
  	   Cart cart = cartService.getCartByUserId(userId).get();
        Optional<Cart> cartOpt = cartService.getCartById(cart.getCartId());
        if (cartOpt.isEmpty()) return ResponseEntity.notFound().build();
        BigDecimal total = cartService.calculateCartTotal(cartOpt.get());
        return ResponseEntity.ok(total);
    }

    // Checkout endpoint (can be integrated with payment)
    @PostMapping("/checkout")
    public ResponseEntity<String> checkoutCart() {
    	int userId = provider.getCurrentUser().get().getUserId();
  	    Cart cart = cartService.getCartByUserId(userId).get();
        // Placeholder for payment/checkout logic
        cartItemService.clearCart(cart.getCartId());  // After transaction, clear cart
        return ResponseEntity.ok("Checkout complete. Cart cleared.");
    }

    // List all carts (admin/debug)
    @GetMapping("/all")
    public ResponseEntity<List<Cart>> getAllCarts() {
    	System.out.println(provider.getCurrentUser());
        return ResponseEntity.ok(cartService.getAllCarts());
    }
} 

