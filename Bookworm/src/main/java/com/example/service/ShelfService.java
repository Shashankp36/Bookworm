package com.example.service;

import com.example.model.*;
import com.example.repository.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShelfService implements IShelf{

    private final ShelfRepository shelfRepository;

    @Autowired
    public ShelfService(ShelfRepository shelfRepository) {
        this.shelfRepository = shelfRepository;
    }

    public Shelf createOrUpdateShelf(Shelf shelf) {
        return shelfRepository.save(shelf);
    }

    public Optional<Shelf> getShelfById(Integer id) {
        return shelfRepository.findById(id);
    }

    public Optional<Shelf> getShelfByUserId(Integer userId) {
        return shelfRepository.findByUser_UserId(userId);
    }

    public void deleteShelfById(Integer id) {
        shelfRepository.deleteById(id);
    }
}
