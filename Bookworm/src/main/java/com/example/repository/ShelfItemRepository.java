package com.example.repository;

import com.example.model.ShelfItem;
import com.example.model.ShelfItem.AccessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import java.util.List;

public interface ShelfItemRepository extends JpaRepository<ShelfItem, Integer> {

    List<ShelfItem> findByShelfShelfId(int shelfId);

    List<ShelfItem> findByShelfShelfIdAndAccessType(int shelfId, AccessType accessType);

    @Transactional
    @Modifying
    @Query("DELETE FROM ShelfItem s WHERE s.rental.rentalId = :rentalId")
    void deleteByRentalId(int rentalId);
}
