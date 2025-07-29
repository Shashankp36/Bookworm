package com.example.service;

import com.example.model.Transaction;
import java.util.List;
import java.util.Optional;

public interface ITransactionService {
    Transaction saveTransaction(Transaction transaction);
    Optional<Transaction> getTransactionById(int id);
    List<Transaction> getAllTransactions();
    void deleteTransaction(int id);
}
