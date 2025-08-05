package com.example.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    // Removed HttpSession from method signature
    public ResponseEntity<String> signup(@RequestBody User user) { 

        if (userService.existsByEmail(user.getUserEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        if (userService.existsByPhone(user.getUserPhone())) {
            return ResponseEntity.badRequest().body("Phone already exists");
        }

        if (user.getRole() == null) {
            user.setRole(User.Role.USER);
        }

        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        User savedUser = userService.createUser(user);

        Shelf shelf = new Shelf();
        shelf.setUser(savedUser);
        shelfService.createOrUpdateShelf(shelf);

        Cart cart = new Cart();
        cart.setUser(savedUser);
        cartService.saveCart(cart);

        // Removed session.setAttribute()

        
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

        if (!passwordEncoder.matches(request.getPassword(), user.getUserPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        String accessToken = jwtUtil.generateToken(user.getUserEmail(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUserEmail());
     //   session.setAttribute("user", user);
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

    // ---------- LOGOUT (CONCEPTUAL) ----------
    // In a stateless JWT system, true logout is handled client-side by deleting the token.
    // A server-side endpoint might be used to blacklist the token if needed, but session.invalidate() is incorrect.
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // The client is responsible for deleting the JWT.
        // The server cannot "invalidate" a JWT without a blacklist mechanism.
        // This endpoint can simply acknowledge the client's action.
        SecurityContextHolder.clearContext(); // Clear security context for the current thread
        return ResponseEntity.ok("Logout successful. Please delete your token on the client-side.");
    }
}