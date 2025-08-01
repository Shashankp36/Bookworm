package com.example.controller;

import com.example.model.*;
import com.example.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;

    @Autowired
    private IUser userService;

    @Autowired
    private IProduct productService;

    // 1. Create or update an order
    @PostMapping
    public Order createOrUpdateOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    // 2. Get all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // 3. Get order by ID
    @GetMapping("/{orderId}")
    public Optional<Order> getOrderById(@PathVariable int orderId) {
        return orderService.getOrderById(orderId);
    }

    // 4. Get orders by user ID
    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable int userId) {
        Optional<User> userOpt = userService.getUserById(userId);
        return userOpt.map(orderService::getOrdersByUser).orElse(null);
    }

    // 5. Delete order by ID
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable int orderId) {
        orderService.deleteOrder(orderId);
    }

    // 6. Update order status
    @PutMapping("/{orderId}/status")
    public boolean updateOrderStatus(@PathVariable int orderId,
                                     @RequestParam Order.OrderStatus status) {
        return orderService.updateOrderStatus(orderId, status);
    }

    // ───────────── OrderDetail Section ─────────────

    // 7. Create or update an OrderDetail
    @PostMapping("/{orderId}/details")
    public OrderDetail addOrderDetail(@PathVariable int orderId,
                                      @RequestParam int productId,
                                      @RequestParam int quantity) {
        Optional<Order> orderOpt = orderService.getOrderById(orderId);
        Optional<Product> productOpt = productService.getProductById(productId);

        if (orderOpt.isPresent() && productOpt.isPresent()) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(orderOpt.get());
            detail.setProduct(productOpt.get());
            return orderDetailService.saveOrUpdateOrderDetail(detail);
        }
        return null;
    }

    // 8. Get all OrderDetails
    @GetMapping("/details")
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailService.getAllOrderDetails();
    }

    // 9. Get OrderDetail by ID
    @GetMapping("/details/{detailId}")
    public Optional<OrderDetail> getOrderDetailById(@PathVariable int detailId) {
        return orderDetailService.getOrderDetailById(detailId);
    }

    // 10. Get OrderDetails for a specific Order
    @GetMapping("/{orderId}/details")
    public List<OrderDetail> getOrderDetailsByOrder(@PathVariable int orderId) {
        Optional<Order> orderOpt = orderService.getOrderById(orderId);
        return orderOpt.map(orderDetailService::getOrderDetailsByOrder).orElse(null);
    }

    // 11. Delete OrderDetail by ID
    @DeleteMapping("/details/{detailId}")
    public void deleteOrderDetail(@PathVariable int detailId) {
        orderDetailService.deleteOrderDetailById(detailId);
    }
}
