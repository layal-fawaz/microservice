package com.AI_project.planning_service.controllers;

import com.AI_project.planning_service.VO.ResponseVO;
import com.AI_project.planning_service.models.ProjectPlan;
import com.AI_project.planning_service.services.ProjectPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/plans")
public class ProjectPlanController {

    @Autowired
    private ProjectPlanService service;

    @GetMapping
    public List<ProjectPlan> getAll() {
        return service.getAllPlans();
    }

    @PostMapping
    public ProjectPlan add(@RequestBody ProjectPlan plan) {
        return service.addPlan(plan);
    }

    @GetMapping("/full-details/{id}")
    public ResponseVO getDetails(@PathVariable Long id) {
        return service.getPlanWithTeamDetails(id);
    }
}
