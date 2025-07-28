package com.example.controller;



import com.example.model.*;
import com.example.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IUser userService;
    private final IShelf shelfService;
    
   // private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, ShelfService shelfService) {
        this.userService = userService;
        this.shelfService = shelfService;
//        this.passwordEncoder = passwordEncoder;
    }

    // ---------- SIGN UP ----------
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        if (userService.existsByEmail(user.getUserEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        if (userService.existsByPhone(user.getUserPhone())) {
            return ResponseEntity.badRequest().body("Phone already exists");
        }
        
       // user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

        // Save User
        User savedUser = userService.createUser(user);

        // Create Shelf for the user
        Shelf shelf = new Shelf();
        shelf.setUser(savedUser);
        shelfService.createOrUpdateShelf(shelf);

        return ResponseEntity.ok("User registered successfully");
    }

    // ---------- LOGIN ----------
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOpt = userService.getUserByEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        User user = userOpt.get();

//        if (!passwordEncoder.matches(password, user.getUserPassword())) {
//            return ResponseEntity.status(401).body("Invalid email or password");
//        }
        
        if (password.equals(user.getUserPassword())) {
          return ResponseEntity.status(401).body("Invalid email or password");
      }

        return ResponseEntity.ok("Login successful");
    }
}
