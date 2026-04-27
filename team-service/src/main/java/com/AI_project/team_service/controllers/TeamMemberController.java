package com.AI_project.team_service.controllers;
import com.AI_project.team_service.models.TeamMember;
import com.AI_project.team_service.repositories.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/team")
public class TeamMemberController {

    @Autowired
    private TeamMemberRepository repository;

    @GetMapping("/{id}")
    public TeamMember getMemberById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }
}