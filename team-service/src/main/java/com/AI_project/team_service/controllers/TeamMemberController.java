package com.AI_project.team_service.controllers;

import com.AI_project.team_service.models.TeamMember;
import com.AI_project.team_service.repositories.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/team")
public class TeamMemberController {

    @Autowired
    private TeamMemberRepository repository;

    @GetMapping
    public List<TeamMember> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamMember> getMemberById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TeamMember> addMember(@RequestBody TeamMember member) {
        return ResponseEntity.ok(repository.save(member));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamMember> updateMember(@PathVariable Long id, @RequestBody TeamMember updated) {
        return repository.findById(id).map(m -> {
            m.setName(updated.getName());
            m.setSkill(updated.getSkill());
            m.setAvailable(updated.isAvailable());
            return ResponseEntity.ok(repository.save(m));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
