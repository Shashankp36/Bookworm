package com.example.repository;

import com.example.model.CartItem;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByCartCartId(int cartId);

    Optional<CartItem> findByCartCartIdAndProductProductId(int cartId, int productId);

    
        @Transactional
        @Modifying
        @Query("DELETE FROM CartItem c WHERE c.cart.cartId = :cartId AND c.product.productId = :productId")
        void deleteByCartIdAndProductId(@Param("productId") int productId, @Param("cartId") int cartId);

}
