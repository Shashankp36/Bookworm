package com.example.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.configuration.SessionUserProvider;
import com.example.model.Cart;
import com.example.model.CartItem;
import com.example.model.Order;
import com.example.model.Purchase;
import com.example.model.Rental;
import com.example.model.Transaction;
import com.example.model.User;
import com.example.notify.MailClient;
import com.example.notify.MailRequest;
import com.example.service.CartService;
import com.example.service.IRoyaltyPaymentService;
import com.example.service.OrderDetailService;
import com.example.service.OrderService;
import com.example.service.PurchaseService;
import com.example.service.RentalService;
import com.example.service.TransactionService;

@RestController
@RequestMapping("api/order")
public class OrdersController {

    @Autowired private TransactionService transactionService;
    @Autowired private OrderService orderService;
    @Autowired private OrderDetailService orderDetailService;
    @Autowired private PurchaseService purchaseService;
    @Autowired private RentalService rentalService;
    @Autowired private CartService cartService;
    @Autowired private SessionUserProvider provider;
    @Autowired private IRoyaltyPaymentService royaltyService;
    @Autowired private MailClient mailClient;

    @PostMapping("/pay")
    public ResponseEntity<String> placeOrder(@RequestParam BigDecimal amount,
                                             @RequestParam String paymentMode) {

        User user = provider.getCurrentUser().get();
        int userId = user.getUserId();

        Order order = orderService.createOrder(userId, amount);
        Transaction txn = transactionService.createTransaction(userId, order, amount, paymentMode, true);

        Cart cart = cartService.getCartEntityByUserId(userId);
        List<CartItem> items = cart.getCartItems();

        orderDetailService.saveOrderDetails(order, items);
        for (CartItem item : items) {
            if (item.getItemType() == CartItem.ItemType.PURCHASE) {
                Purchase purchase = purchaseService.save(order, item);
                royaltyService.saveRoyalty(purchase, item, order);
            } else {
                Rental rental = rentalService.save(order, item);
                royaltyService.saveRoyalty(rental, item, order);
            }
        }
        sendInvoiceEmail(user, order, items, amount);
        cartService.clearCart(cart);
        return ResponseEntity.ok("✅ Order placed successfully. Transaction ID: " + txn.getTransactionId());
    }

    public void sendInvoiceEmail(User user, Order order, List<CartItem> items, BigDecimal amount) {
        StringBuilder itemRows = new StringBuilder();
        
        for (CartItem item : items) {
            String bookTitle = item.getProduct().getTitle();
            String format = item.getItemType().toString();
            BigDecimal price = item.getProduct().getPrice();
//            int quantity = item.getQuantity();
            BigDecimal total = order.getTotalAmount();
  
            itemRows.append("<tr>")
                    .append("<td>").append(bookTitle).append("</td>")
                    .append("<td>").append(format).append("</td>")
                    .append("<td>₹").append(price).append("</td>")
//                    .append("<td>").append(quantity).append("</td>")
//                    .append("<td>₹").append(total).append("</td>")
                    .append("</tr>");
        }

        String invoiceHtml = "<!DOCTYPE html><html><head><meta charset='UTF-8'>"
                + "<style>body{font-family:Arial,sans-serif;} .invoice-box{max-width:800px;margin:auto;padding:30px;border:1px solid #eee;box-shadow:0 0 10px rgba(0,0,0,0.15);}"
                + "table{width:100%;border-collapse:collapse;} th,td{padding:10px;text-align:left;border:1px solid #ddd;} th{background-color:#f2f2f2;} .total{font-weight:bold;}</style>"
                + "</head><body><div class='invoice-box'>"
                + "<h2>Bookworm - Invoice</h2>"
                + "<p><strong>Invoice ID:</strong> " + order.getOrderId() + "<br>"
                + "<strong>Date:</strong> " + order.getOrderDate() + "<br>"
                + "<strong>Customer Name:</strong> " + user.getUserName() + "<br>"
                + "<strong>Email:</strong> " + user.getUserEmail() + "</p>"
                + "<table><thead><tr><th>Book Title</th><th>Format</th><th>Price</th></tr></thead><tbody>"
                + itemRows.toString()
                + "<tr class='total'><td colspan='2' style='text-align:right;'>Grand Total:</td><td>₹" + amount + "</td></tr>"
                + "</tbody></table><p style='margin-top:30px;'>Thank you for shopping with Bookworm!</p></div></body></html>";

        MailRequest request = new MailRequest();
        request.setTo(user.getUserEmail());
        request.setSubject("Thank You for Your Purchase – Invoice #" + order.getOrderId());
        request.setMessage(invoiceHtml);

        try {
            String response = mailClient.sendMail(request);
            System.out.println("✅ Email sent: " + response);
        } catch (Exception e) {
            System.err.println("❌ Email failed: " + e.getMessage());
        }
    }
}
