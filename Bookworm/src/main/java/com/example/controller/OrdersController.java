package com.example.controller;

import com.example.configuration.SessionUserProvider;
import com.example.model.*;
import com.example.security.JwtUtil;
import com.example.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired private TransactionService transactionService;
    @Autowired private OrderService orderService;
    @Autowired private OrderDetailService orderDetailService;
    @Autowired private PurchaseService purchaseService;
    @Autowired private RentalService rentalService;
    @Autowired private RoyaltyPaymentService royaltyPaymentService;
    @Autowired private CartService cartService;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private SessionUserProvider provider;
    @Autowired private IRoyaltyPaymentService royaltyService;

    @PostMapping("/pay")
    public ResponseEntity<String> placeOrder(  @RequestParam BigDecimal amount,
    		@RequestParam String paymentMode) {
        // ✅ Get userId from session
        int userId = provider.getCurrentUser().get().getUserId();

        // ✅ Proceed like your service
        Order order = orderService.createOrder(userId, amount);
        Transaction txn = transactionService.createTransaction(userId, order, amount, paymentMode, true);

        Cart cart = cartService.getCartEntityByUserId(userId);
        List<CartItem> items = cart.getCartItems();

        orderDetailService.saveOrderDetails(order, items);
        for (CartItem item : items) {
            if (item.getItemType() == CartItem.ItemType.PURCHASE) {
                purchaseService.save(order, item);
                
            } else {
                rentalService.save(order, item);
            }
        }
        

//        royaltyPaymentService.generate(order, items);
        cartService.clearCart(cart);

        return ResponseEntity.ok("✅ Order placed successfully. Transaction ID: " + txn.getTransactionId());
    }

}
