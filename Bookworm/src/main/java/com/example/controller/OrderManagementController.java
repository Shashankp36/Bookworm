package com.example.controller;

import com.example.model.*;
import com.example.service.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderManagementController {

    @Autowired private IOrderService orderService;
    @Autowired private IOrderDetailService orderDetailService;
    @Autowired private IUser userService;
    @Autowired private IProduct productService;

    // 1. Create or update an Order
    @PostMapping
    public Order createOrUpdateOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    // 2. Get all Orders (Admin use)
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // 3. Get Order by ID (checks user ownership)
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable int orderId, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        Optional<Order> orderOpt = orderService.getOrderById(orderId);

        if (orderOpt.isPresent() && orderOpt.get().getUser().getUserId() == userId) {
            return orderOpt.get();
        }
        throw new RuntimeException("Unauthorized or order not found.");
    }

    // 4. Get Orders for current logged-in user
    @GetMapping("/user")
    public List<Order> getOrdersForLoggedInUser(HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        Optional<User> userOpt = userService.getUserById(userId);
        return userOpt.map(orderService::getOrdersByUser).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 5. Delete Order by ID (admin or internal use)
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable int orderId) {
        orderService.deleteOrder(orderId);
    }

    // 6. Update Order Status
    @PutMapping("/{orderId}/status")
    public boolean updateOrderStatus(@PathVariable int orderId,
                                     @RequestParam Order.OrderStatus status) {
        return orderService.updateOrderStatus(orderId, status);
    }

    // ───────────── OrderDetail Section ─────────────

    // 7. Add OrderDetail to Order (used internally)
    @PostMapping("/{orderId}/details")
    public OrderDetail addOrderDetail(@PathVariable int orderId,
                                      @RequestParam int productId) {
        Optional<Order> orderOpt = orderService.getOrderById(orderId);
        Optional<Product> productOpt = productService.getProductById(productId);

        if (orderOpt.isPresent() && productOpt.isPresent()) {
            Order order = orderOpt.get();
            Product product = productOpt.get();

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);

            String formatName = (product.getFormat() != null) ? product.getFormat().getFormatName() : "Unknown";
            detail.setProductType(formatName);
          

            return orderDetailService.saveOrUpdateOrderDetail(detail);
        }

        return null;
    }

    // 8. Get all OrderDetails (admin or debugging use)
    @GetMapping("/details")
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailService.getAllOrderDetails();
    }

    // 9. Get OrderDetail by ID (internal)
    @GetMapping("/details/{detailId}")
    public Optional<OrderDetail> getOrderDetailById(@PathVariable int detailId) {
        return orderDetailService.getOrderDetailById(detailId);
    }

    // 10. Get OrderDetails by Order ID (checks if order belongs to logged-in user)
    @GetMapping("/{orderId}/details")
    public List<OrderDetail> getOrderDetailsByOrder(@PathVariable int orderId, HttpSession session) {
        int userId = (int) session.getAttribute("userId");

        Optional<Order> orderOpt = orderService.getOrderById(orderId);
        if (orderOpt.isPresent() && orderOpt.get().getUser().getUserId() == userId) {
            return orderDetailService.getOrderDetailsByOrder(orderOpt.get());
        }

        throw new RuntimeException("Unauthorized or order not found.");
    }

    // 11. Delete OrderDetail by ID (internal)
    @DeleteMapping("/details/{detailId}")
    public void deleteOrderDetail(@PathVariable int detailId) {
        orderDetailService.deleteOrderDetailById(detailId);
    }

    // 12. Get Order Status for specific order
    @GetMapping("/{orderId}/status")
    public String getOrderStatus(@PathVariable int orderId, HttpSession session) {
        int userId = (int) session.getAttribute("userId");

        Optional<Order> orderOpt = orderService.getOrderById(orderId);
        if (orderOpt.isPresent() && orderOpt.get().getUser().getUserId() == userId) {
            return orderOpt.get().getOrderStatus().name();
        }

        throw new RuntimeException("Unauthorized or order not found.");
    }
}
