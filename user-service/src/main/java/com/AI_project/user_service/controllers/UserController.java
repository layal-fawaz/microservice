package com.AI_project.user_service.controllers;

import com.AI_project.user_service.models.User;
import com.AI_project.user_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    @Autowired
    private UserService service;

    // POST /api/v1/auth/register
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(service.register(user));
    }

    // POST /api/v1/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        return service.login(email, password)
                .map(u -> ResponseEntity.ok(Map.of(
                        "user_id", u.getId(),
                        "email", u.getEmail(),
                        "auth_token", u.getAuthToken(),
                        "role", u.getRole() != null ? u.getRole() : "USER"
                )))
                .orElse(ResponseEntity.status(401).body(Map.of("error", "Invalid credentials")));
    }

    // GET /api/v1/auth/validate?token=...
    @GetMapping("/validate")
    public ResponseEntity<Map<String, Boolean>> validate(@RequestParam String token) {
        boolean valid = service.validateToken(token);
        return ResponseEntity.ok(Map.of("is_valid", valid));
    }

    // GET /api/v1/auth/users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    // GET /api/v1/auth/users/{id}
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return service.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
