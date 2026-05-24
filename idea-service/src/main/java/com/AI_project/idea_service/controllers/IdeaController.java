package com.AI_project.idea_service.controllers;

import com.AI_project.idea_service.models.Idea;
import com.AI_project.idea_service.services.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ideas")
public class IdeaController {

    @Autowired
    private IdeaService service;

    @PostMapping
    public ResponseEntity<Idea> submitIdea(@RequestBody Idea idea) {
        return ResponseEntity.ok(service.submitIdea(idea));
    }

    @GetMapping
    public List<Idea> getAllIdeas() {
        return service.getAllIdeas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Idea> getById(@PathVariable Long id) {
        return service.getIdeaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Idea> getByUser(@PathVariable Long userId) {
        return service.getIdeasByUser(userId);
    }

    // POST /api/v1/ideas/{id}/analyze  => validates subscription then sends to planning
    @PostMapping("/{id}/analyze")
    public ResponseEntity<Map<String, Object>> analyzeIdea(@PathVariable Long id) {
        return ResponseEntity.ok(service.analyzeIdea(id));
    }
}
