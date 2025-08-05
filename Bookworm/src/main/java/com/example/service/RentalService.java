package com.example.service;

import com.example.model.Rental;
import com.example.model.CartItem;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.Date;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService implements IRentalService {

    private final RentalRepository rentalRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    // Create or update a rental
    @Override
    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    // Get all rentals
    @Override
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    // Get rental by ID
    @Override
    public Optional<Rental> getRentalById(int id) {
        return rentalRepository.findById(id);
    }

    // Get rentals by user
    @Override
    public List<Rental> getRentalsByUser(User user) {
        return rentalRepository.findByUser(user);
    }

    // Get rentals by product
    @Override
    public List<Rental> getRentalsByProduct(Product product) {
        return rentalRepository.findByProduct(product);
    }

    // Get active rentals (not yet ended)
    @Override
    public List<Rental> getActiveRentals() {
        return rentalRepository.findByRentalEndAfter(LocalDate.now());
    }

    // Delete rental
    @Override
    public void deleteRental(int id) {
        rentalRepository.deleteById(id);
    }


    @Override
    public void checkExpiry(User user) {
        List<Rental> rentals = this.getRentalsByUser(user);
        LocalDate currDate = LocalDate.now();
        
        for (Rental rental : rentals) {
            if (currDate.isAfter(rental.getRentalEnd())) {
                this.deleteRental(rental.getRentalId());
                System.out.println("Rental expired: " + rental.getRentalId());
                rentalRepository.save(rental);
            }
        }
    }


    // 8. Save rental from order + cart item
    @Override
    public Rental save(Order order, CartItem item) {
        Rental rental = new Rental();
        rental.setOrder(order);
        rental.setUser(order.getUser());
        rental.setProduct(item.getProduct());

        // Rental is for 7 days
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);

        rental.setRentalStart(startDate);
        rental.setRentalEnd(endDate);

        BigDecimal basePrice = item.getProduct().getPrice();
        BigDecimal rentalPrice = basePrice.multiply(BigDecimal.valueOf(0.20));

        rental.setPricePaid(rentalPrice);

        // Dummy royalties
        rental.setAuthorRoyalty(rentalPrice.multiply(BigDecimal.valueOf(0.10)));
        rental.setPublisherRoyalty(rentalPrice.multiply(BigDecimal.valueOf(0.05)));
        rental.setRoyaltyType(Rental.RoyaltyType.percentage);

        return rentalRepository.save(rental);
    }

}
