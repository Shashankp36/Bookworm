package com.example.service;

import com.example.model.ShelfItem;
import java.util.List;
import java.util.Optional;

public interface IShelfItemService {
    ShelfItem saveShelfItem(ShelfItem shelfItem);
    Optional<ShelfItem> getShelfItemById(int id);
    List<ShelfItem> getAllShelfItems();
    void deleteShelfItem(int id);
}
