package com.example.service;

import com.example.model.Rental;
import java.util.List;
import java.util.Optional;

public interface IRentalService {
    Rental saveRental(Rental rental);
    Optional<Rental> getRentalById(int id);
    List<Rental> getAllRentals();
    void deleteRental(int id);
}
