package com.example.repository;

import com.example.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByCartCartId(int cartId);

    Optional<CartItem> findByCartCartIdAndProductProductId(int cartId, int productId);
}
