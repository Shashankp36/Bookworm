package com.example.repository;
import com.example.model.*;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelfRepository extends JpaRepository<Shelf, Integer> {
	Optional<Shelf> findByUser_UserId(Integer userId);
}
