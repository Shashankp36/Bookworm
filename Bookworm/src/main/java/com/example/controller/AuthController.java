package com.example.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UserLoginDTO;
import com.example.model.Cart;
import com.example.model.Shelf;
import com.example.model.User;
import com.example.security.JwtUtil;
import com.example.service.CartService;
import com.example.service.ICartService;
import com.example.service.IShelf;
import com.example.service.IUser;
import com.example.service.ShelfService;
import com.example.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IUser userService;
    private final IShelf shelfService;
    private final ICartService cartService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, ShelfService shelfService, CartService cartService) {
        this.userService = userService;
        this.shelfService = shelfService;
        this.cartService = cartService;
    }

    // ---------- SIGN UP ----------
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user , HttpSession session) {
        if (userService.existsByEmail(user.getUserEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        if (userService.existsByPhone(user.getUserPhone())) {
            return ResponseEntity.badRequest().body("Phone already exists");
        }

        // Set default role if not provided
        if (user.getRole() == null) {
            user.setRole(User.Role.USER);
        }

        // Encode password before saving
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

        // Save user
        User savedUser = userService.createUser(user);

        // Create and associate shelf
        Shelf shelf = new Shelf();
        shelf.setUser(savedUser);
        shelfService.createOrUpdateShelf(shelf);

        // Create and associate cart
        Cart cart = new Cart();
        cart.setUser(savedUser);
        cartService.saveCart(cart);
        
        session.setAttribute("user", savedUser);
        
        return ResponseEntity.ok("User registered successfully with shelf and cart");
    }

    // ---------- LOGIN ----------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO request) {
        Optional<User> userOpt = userService.getUserByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        User user = userOpt.get();

        // Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getUserPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        // Generate tokens
        String accessToken = jwtUtil.generateToken(user.getUserEmail(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserEmail());

        return ResponseEntity.ok().body(Map.of(
            "message", "Login successful",
            "accessToken", accessToken,
            "refreshToken", refreshToken,
            "role", user.getRole().name()
        ));
    }

    // ---------- REFRESH TOKEN ----------
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam String refreshToken) {
        if (!jwtUtil.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(401).body("Invalid or expired refresh token");
        }

        String email = jwtUtil.extractEmail(refreshToken);
        Optional<User> userOpt = userService.getUserByEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("User not found");
        }

        User user = userOpt.get();
        String newAccessToken = jwtUtil.generateToken(email, user.getRole().name());

        return ResponseEntity.ok().body(Map.of(
            "accessToken", newAccessToken,
            "role", user.getRole().name()
        ));
    }

    // ---------- LOGOUT ----------
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // Ends the user session
        return ResponseEntity.ok("Logout successful. Session invalidated.");
    }
}
