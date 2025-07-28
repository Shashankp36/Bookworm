package com.example.repository;

import com.example.model.ShelfItem;
import com.example.model.ShelfItem.AccessType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShelfItemRepository extends JpaRepository<ShelfItem, Integer> {

    List<ShelfItem> findByShelfShelfId(int shelfId);

    List<ShelfItem> findByShelfShelfIdAndAccessType(int shelfId, AccessType accessType);

    //boolean existsByShelfShelfIdAndAccessTypeAndProductId(int shelfId, AccessType accessType, int productId);
}
