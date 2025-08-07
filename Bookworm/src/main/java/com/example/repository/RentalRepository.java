package com.example.repository;

import com.example.model.Product;
import com.example.model.Purchase;
import com.example.model.Rental;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
    List<Rental> findByUser(User user);
    List<Rental> findByUserUserId(int userId);
    List<Rental> findByProduct(Product product);
    List<Rental> findByRentalEndAfter(LocalDate date);
}
