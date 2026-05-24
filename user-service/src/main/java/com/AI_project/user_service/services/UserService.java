package com.AI_project.user_service.services;

import com.AI_project.user_service.models.User;
import com.AI_project.user_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User register(User user) {
        user.setAuthToken(UUID.randomUUID().toString());
        return repository.save(user);
    }

    public Optional<User> login(String email, String password) {
        return repository.findByEmail(email)
                .filter(u -> u.getPassword().equals(password))
                .map(u -> {
                    u.setAuthToken(UUID.randomUUID().toString());
                    return repository.save(u);
                });
    }

    public boolean validateToken(String token) {
        return repository.findAll().stream()
                .anyMatch(u -> token.equals(u.getAuthToken()));
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }
}
