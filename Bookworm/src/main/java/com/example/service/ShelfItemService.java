package com.example.service;

import com.example.model.ShelfItem;
import com.example.model.ShelfItem.AccessType;
import com.example.repository.ShelfItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShelfItemService implements IShelfItemService {

    private final ShelfItemRepository shelfItemRepository;

    @Autowired
    public ShelfItemService(ShelfItemRepository shelfItemRepository) {
        this.shelfItemRepository = shelfItemRepository;
    }

    // 1. Add or update a shelf item
    @Override
    public ShelfItem saveShelfItem(ShelfItem item) {
        return shelfItemRepository.save(item);
    }

    // 2. Get all shelf items
    @Override
    public List<ShelfItem> getAllShelfItems() {
        return shelfItemRepository.findAll();
    }

    // 3. Get shelf item by ID
    @Override
    public Optional<ShelfItem> getShelfItemById(int id) {
        return shelfItemRepository.findById(id);
    }

    // 4. Delete shelf item
    @Override
    public void deleteShelfItem(int id) {
        shelfItemRepository.deleteById(id);
    }

    // 5. Get items by Shelf ID
    public List<ShelfItem> getItemsByShelfId(int shelfId) {
        return shelfItemRepository.findByShelfShelfId(shelfId);
    }

    // 6. Get purchased items by Shelf ID
    public List<ShelfItem> getPurchasedItemsByShelfId(int shelfId) {
        return shelfItemRepository.findByShelfShelfIdAndAccessType(shelfId, AccessType.purchase);
    }

    // 7. Get rented items by Shelf ID
    public List<ShelfItem> getRentedItemsByShelfId(int shelfId) {
        return shelfItemRepository.findByShelfShelfIdAndAccessType(shelfId, AccessType.rental);
    }

    // 8. Check if a product already exists in the shelf with specific access type
//    public boolean isProductInShelf(int shelfId, int productId, AccessType type) {
//        return shelfItemRepository.existsByShelfShelfIdAndAccessTypeAndProductProductId(shelfId, type, productId);
//    }

    // 9. Update shelf item
    public ShelfItem updateShelfItem(ShelfItem item) {
        return shelfItemRepository.save(item); // Save also updates if ID is present
    }
}
