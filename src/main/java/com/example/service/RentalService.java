package com.example.service;

import com.example.model.Product;
import com.example.model.Rental;
import com.example.model.User;
import com.example.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    // Create or update a rental
    public Rental saveOrUpdateRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    // Get all rentals
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    // Get rental by ID
    public Optional<Rental> getRentalById(int id) {
        return rentalRepository.findById(id);
    }

    // Get rentals by user
    public List<Rental> getRentalsByUser(User user) {
        return rentalRepository.findByUser(user);
    }

    // Get rentals by product
    public List<Rental> getRentalsByProduct(Product product) {
        return rentalRepository.findByProduct(product);
    }

    // Get active rentals (not yet ended)
    public List<Rental> getActiveRentals() {
        return rentalRepository.findByRentalEndAfter(LocalDate.now());
    }

    // Delete rental
    public void deleteRentalById(int id) {
        rentalRepository.deleteById(id);
    }
}
