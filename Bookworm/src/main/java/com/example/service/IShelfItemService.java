package com.example.service;

import com.example.model.ShelfItem;
import com.example.model.ShelfItem.AccessType;

import java.util.List;
import java.util.Optional;

public interface IShelfItemService {
    ShelfItem saveShelfItem(ShelfItem shelfItem);
    Optional<ShelfItem> getShelfItemById(int id);
    List<ShelfItem> getAllShelfItems();
    void deleteShelfItem(int id);

    List<ShelfItem> getItemsByShelfId(int shelfId);
    List<ShelfItem> getPurchasedItemsByShelfId(int shelfId);
    List<ShelfItem> getRentedItemsByShelfId(int shelfId);
    ShelfItem updateShelfItem(ShelfItem shelfItem);
    void deleteShelfByRentalId(int rentalId);
    // Optional: if you later enable duplicate-checking for adding products to shelf
    // boolean isProductInShelf(int shelfId, int productId, AccessType accessType);
}
