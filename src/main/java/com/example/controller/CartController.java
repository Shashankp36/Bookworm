package com.example.controller;

import com.example.model.*;
import com.example.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private IProduct productService; 

    @Autowired
    private IDiscountService discountService;

    @Autowired
    private IUser userService; 

    // 1. Create Cart for a user
    @PostMapping("/create/{userId}")
    public Cart createCart(@PathVariable int userId) {
        Optional<User> userOpt = userService.getUserById(userId);
        return userOpt.map(cartService::createCartForUser).orElse(null);
    }

    // 2. Get Cart by User ID
    @GetMapping("/user/{userId}")
    public Optional<Cart> getCartByUserId(@PathVariable int userId) {
        return cartService.getCartByUserId(userId);
    }

    // 3. Get Cart by Cart ID
    @GetMapping("/{cartId}")
    public Optional<Cart> getCartById(@PathVariable int cartId) {
        return cartService.getCartById(cartId);
    }

    // 4. Add/Update Item in Cart
    @PostMapping("/{cartId}/add/{productId}")
    public CartItem addOrUpdateItem(@PathVariable int cartId,
                                    @PathVariable int productId,
                                    @RequestParam int quantity) {
        Optional<Cart> cartOpt = cartService.getCartById(cartId);
        Optional<Product> productOpt = productService.getProductById(productId);
        if (cartOpt.isPresent() && productOpt.isPresent()) {
            return cartItemService.addOrUpdateItem(cartOpt.get(), productOpt.get(), quantity);
        }
        return null;
    }

    // 5. Get all items in a Cart
    @GetMapping("/{cartId}/items")
    public List<CartItem> getCartItems(@PathVariable int cartId) {
        return cartItemService.getItemsByCartId(cartId);
    }

    // 6. Update quantity of a CartItem
    @PutMapping("/item/{cartItemId}/quantity")
    public CartItem updateCartItemQuantity(@PathVariable int cartItemId,
                                           @RequestParam int quantity) {
        return cartItemService.updateCartItemQuantity(cartItemId, quantity);
    }

    // 7. Apply Discount to CartItem
    @PutMapping("/item/{cartItemId}/discount/{discountId}")
    public CartItem applyDiscount(@PathVariable int cartItemId,
                                  @PathVariable int discountId) {
        Optional<Discount> discountOpt = discountService.getDiscountById(discountId);
        return discountOpt.map(discount -> cartItemService.applyDiscountToCartItem(cartItemId, discount)).orElse(null);
    }

    // 8. Remove CartItem
    @DeleteMapping("/item/{cartItemId}")
    public void removeCartItem(@PathVariable int cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
    }

    // 9. Clear all items in a cart
    @DeleteMapping("/{cartId}/clear")
    public void clearCart(@PathVariable int cartId) {
        cartItemService.clearCart(cartId);
    }

}
