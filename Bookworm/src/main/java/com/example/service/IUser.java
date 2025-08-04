package com.example.service;

import java.util.Optional;

import com.example.model.User;
import com.example.repository.UserRepository;

public interface IUser {

	   public User createUser(User user) ;
	   public Optional<User> getUserById(Integer id);
	   public Optional<User> getUserByEmail(String email);
	   public boolean existsByEmail(String email);
	   public boolean existsByPhone(String phone);
	   public User updateUser(User user);
	   public void deleteUserById(Integer id);
}
