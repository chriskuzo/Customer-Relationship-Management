package com.mahwi.backend.auth.controller;

import com.mahwi.backend.auth.model.User;
import com.mahwi.backend.auth.payload.LoginRequest;
import com.mahwi.backend.auth.payload.RegisterRequest;
import com.mahwi.backend.auth.payload.TokenResponse;
import com.mahwi.backend.auth.payload.UserDto;
import com.mahwi.backend.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Authentication", description = "Public authentication endpoints")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Public endpoint - No authentication required")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getIdentifier());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());
        user.setPassword(request.getPassword()); // ✅ FIXED — now password is set

        User savedUser = authService.register(user, request.getRole());

        UserDto dto = new UserDto();
        dto.setId(savedUser.getId());
        dto.setUsername(savedUser.getUsername());
        dto.setEmail(savedUser.getEmail());

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Public endpoint - Returns JWT token")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getIdentifier(), request.getPassword()); // ✅ FIXED — use identifier
        TokenResponse response = new TokenResponse();
        response.setToken(token);
        return ResponseEntity.ok(response);
    }
}
