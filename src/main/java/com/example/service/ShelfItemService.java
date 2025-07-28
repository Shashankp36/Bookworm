package com.example.service;

import com.example.model.ShelfItem;
import com.example.model.ShelfItem.AccessType;
import com.example.repository.ShelfItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShelfItemService {

    @Autowired
    private ShelfItemRepository shelfItemRepository;

    // 1. Add a new shelf item
    public ShelfItem addShelfItem(ShelfItem item) {
        return shelfItemRepository.save(item);
    }

    // 2. Get all shelf items
    public List<ShelfItem> getAllShelfItems() {
        return shelfItemRepository.findAll();
    }

    // 3. Get a specific shelf item by ID
    public Optional<ShelfItem> getShelfItemById(int id) {
        return shelfItemRepository.findById(id);
    }

    // 4. Get all items for a specific shelf
    public List<ShelfItem> getItemsByShelfId(int shelfId) {
        return shelfItemRepository.findByShelfShelfId(shelfId);
    }

    // 5. Get all purchased items for a shelf
    public List<ShelfItem> getPurchasedItemsByShelfId(int shelfId) {
        return shelfItemRepository.findByShelfShelfIdAndAccessType(shelfId, AccessType.purchase);
    }

    // 6. Get all rented items for a shelf
    public List<ShelfItem> getRentedItemsByShelfId(int shelfId) {
        return shelfItemRepository.findByShelfShelfIdAndAccessType(shelfId, AccessType.rental);
    }

    // 7. Delete a shelf item
    public void deleteShelfItem(int id) {
        shelfItemRepository.deleteById(id);
    }

    // 8. Check if a product already exists in the shelf
//    public boolean isProductInShelf(int shelfId, int productId, AccessType type) {
//        return shelfItemRepository.existsByShelfShelfIdAndAccessTypeAndProductId(shelfId, type, productId);
//    }

    // 9. Update shelf item (optional)
    public ShelfItem updateShelfItem(ShelfItem item) {
        return shelfItemRepository.save(item);
    }
}
