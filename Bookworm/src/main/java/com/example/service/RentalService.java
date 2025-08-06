package com.example.service;

import com.example.model.Rental;
import com.example.model.Shelf;
import com.example.model.ShelfItem;
import com.example.model.CartItem;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.Purchase;
import com.example.model.User;
import com.example.model.ShelfItem.AccessType;
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
    private ShelfItemService  shelfItemService;
    @Autowired
    private ShelfService shelfService; 

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
               
                shelfItemService.deleteShelfByRentalId(rental.getRentalId());

               
                this.deleteRental(rental.getRentalId());

                System.out.println("Rental expired and deleted: " + rental.getRentalId());
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

        
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(item.getDays());

        rental.setRentalStart(startDate);
        rental.setRentalEnd(endDate);

        BigDecimal basePrice = item.getProduct().getRentPerDay();
        BigDecimal rentalPrice = basePrice.multiply(BigDecimal.valueOf(item.getDays()));

        rental.setPricePaid(rentalPrice);
        BigDecimal authorRoyalty = rentalPrice.multiply(item.getProduct().getRoyaltyAuthor().divide(BigDecimal.valueOf(100)));
        BigDecimal publisherRoyalty = rentalPrice.multiply(item.getProduct().getRoyaltyPublisher().divide(BigDecimal.valueOf(100)));
        // Dummy royalties
        rental.setAuthorRoyalty(authorRoyalty);
        rental.setPublisherRoyalty(publisherRoyalty);
        
        
        Rental savedRental = rentalRepository.save(rental); 
        int userId = order.getUser().getUserId();
        Shelf shelf = shelfService.getShelfByUserId(userId).get();
        ShelfItem shelfItem = new ShelfItem();
        shelfItem.setRental(savedRental);
        shelfItem.setFormat(item.getProduct().getFormat());
        shelfItem.setShelf(shelf);
        shelfItem.setAccessType(AccessType.rental);
        shelfItemService.saveShelfItem(shelfItem);

        return savedRental;
    }

}
