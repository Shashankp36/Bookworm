package com.example.controller;

import com.example.model.Transaction;
import com.example.model.Transaction.PaymentStatus;
import com.example.service.ITransactionService;
import com.example.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    // Save or update a transaction
    @PostMapping
    public Transaction saveTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    // Get all transactions
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // Get transaction by ID
    @GetMapping("/{id}")
    public Optional<Transaction> getTransactionById(@PathVariable int id) {
        return transactionService.getTransactionById(id);
    }

    // Delete transaction by ID
    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable int id) {
        transactionService.deleteTransaction(id);
    }

    // Get transactions by User ID
    @GetMapping("/user/{userId}")
    public List<Transaction> getTransactionsByUserId(@PathVariable int userId) {
        return transactionService.getTransactionsByUserId(userId);
    }

    // Get transactions by Order ID
    @GetMapping("/order/{orderId}")
    public List<Transaction> getTransactionsByOrderId(@PathVariable int orderId) {
        return transactionService.getTransactionsByOrderId(orderId);
    }

    // Get transactions by Payment Status
    @GetMapping("/status/{status}")
    public List<Transaction> getTransactionsByStatus(@PathVariable PaymentStatus status) {
        return transactionService.getTransactionsByStatus(status);
    }

    // Get transactions by date range
    @GetMapping("/range")
    public List<Transaction> getTransactionsByDateRange(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return transactionService.getTransactionsByDateRange(from, to);
    }
}
