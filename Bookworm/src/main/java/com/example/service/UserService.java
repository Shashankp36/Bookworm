

package com.example.service;

import com.example.model.*;

import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUser {

    private final UserRepository userRepository;

    @Autowired // Constructor injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        // Optional: Add email/phone uniqueness check
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByUserEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByUserEmail(email);
    }

    public boolean existsByPhone(String phone) {
        return userRepository.existsByUserPhone(phone);
    }

    public User updateUser(User user) {
        return userRepository.save(user); // Works for both insert and update
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }
}
