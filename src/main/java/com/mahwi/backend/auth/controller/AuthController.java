package com.mahwi.backend.auth.controller;

import com.mahwi.backend.auth.model.User;
import com.mahwi.backend.auth.payload.LoginRequest;
import com.mahwi.backend.auth.payload.RegisterRequest;
import com.mahwi.backend.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        // Build User object from request
        User user = new User();
        user.setUsername(request.getIdentifier());
        user.setPassword(request.getPassword()); // Set password on user object
        user.setEmail(request.getEmail()); // if email field exists

        // Call authService with User object and role string
        User registeredUser = authService.register(user, request.getRole());

        return ResponseEntity.ok(Map.of(
            "id", registeredUser.getId(),
            "username", registeredUser.getUsername(),
            "email", registeredUser.getEmail()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request.getIdentifier(), request.getPassword());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
