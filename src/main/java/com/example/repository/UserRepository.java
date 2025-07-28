package com.example.repository;
import com.example.model.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserEmail(String email);
    boolean existsByUserEmail(String email);
    boolean existsByUserPhone(String phone);
}
