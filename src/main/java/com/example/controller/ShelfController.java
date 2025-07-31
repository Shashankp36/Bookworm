package com.example.controller;

import com.example.model.Shelf;
import com.example.model.ShelfItem;
import com.example.service.IShelf;
import com.example.service.IShelfItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shelves")
public class ShelfController {

    @Autowired
    private IShelf shelfService;

    @Autowired
    private IShelfItemService shelfItemService;

    // ────────────── Shelf APIs ──────────────

    // Create or update a shelf
    @PostMapping
    public Shelf createOrUpdateShelf(@RequestBody Shelf shelf) {
        return shelfService.createOrUpdateShelf(shelf);
    }

    // Get shelf by ID
    @GetMapping("/{shelfId}")
    public Optional<Shelf> getShelfById(@PathVariable int shelfId) {
        return shelfService.getShelfById(shelfId);
    }

    // Get shelf by User ID
    @GetMapping("/user/{userId}")
    public Optional<Shelf> getShelfByUserId(@PathVariable int userId) {
        return shelfService.getShelfByUserId(userId);
    }

    // Delete shelf by ID
    @DeleteMapping("/{shelfId}")
    public void deleteShelf(@PathVariable int shelfId) {
        shelfService.deleteShelfById(shelfId);
    }

    // ────────────── Shelf Item APIs ──────────────

    // Add a shelf item
    @PostMapping("/items")
    public ShelfItem addShelfItem(@RequestBody ShelfItem item) {
        return shelfItemService.saveShelfItem(item);
    }

    // Update a shelf item
    @PutMapping("/items")
    public ShelfItem updateShelfItem(@RequestBody ShelfItem item) {
        return shelfItemService.updateShelfItem(item);
    }

    // Get all items in a shelf
    @GetMapping("/{shelfId}/items")
    public List<ShelfItem> getItemsInShelf(@PathVariable int shelfId) {
        return shelfItemService.getItemsByShelfId(shelfId);
    }

    // Get purchased items from a shelf
    @GetMapping("/{shelfId}/items/purchased")
    public List<ShelfItem> getPurchasedItems(@PathVariable int shelfId) {
        return shelfItemService.getPurchasedItemsByShelfId(shelfId);
    }

    // Get rented items from a shelf
    @GetMapping("/{shelfId}/items/rented")
    public List<ShelfItem> getRentedItems(@PathVariable int shelfId) {
        return shelfItemService.getRentedItemsByShelfId(shelfId);
    }

    // Get a shelf item by ID
    @GetMapping("/items/{itemId}")
    public Optional<ShelfItem> getShelfItemById(@PathVariable int itemId) {
        return shelfItemService.getShelfItemById(itemId);
    }

    // Delete a shelf item
    @DeleteMapping("/items/{itemId}")
    public void deleteShelfItem(@PathVariable int itemId) {
        shelfItemService.deleteShelfItem(itemId);
    }
}
