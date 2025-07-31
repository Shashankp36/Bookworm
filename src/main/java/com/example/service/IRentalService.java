package com.example.service;

import com.example.model.Rental;
import com.example.model.Product;
import com.example.model.User;

import java.util.List;
import java.util.Optional;

public interface IRentalService {
    Rental saveRental(Rental rental);
    Optional<Rental> getRentalById(int id);
    List<Rental> getAllRentals();
    void deleteRental(int id);

    List<Rental> getRentalsByUser(User user);
    List<Rental> getRentalsByProduct(Product product);
    List<Rental> getActiveRentals();
}
