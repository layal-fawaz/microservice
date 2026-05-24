package com.AI_project.progress_service.controllers;

import com.AI_project.progress_service.models.ProgressReport;
import com.AI_project.progress_service.services.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/progress")
public class ProgressController {

    @Autowired
    private ProgressService service;

    // Called by task-service (async event simulation)
    @PostMapping("/update")
    public ResponseEntity<ProgressReport> recordUpdate(@RequestBody Map<String, Object> event) {
        return ResponseEntity.ok(service.recordUpdate(event));
    }

    @GetMapping
    public List<ProgressReport> getAllReports() {
        return service.getAllReports();
    }

    @GetMapping("/project/{projectId}")
    public List<ProgressReport> getByProject(@PathVariable Long projectId) {
        return service.getReportsByProject(projectId);
    }

    @GetMapping("/project/{projectId}/summary")
    public ResponseEntity<Map<String, Object>> getSummary(@PathVariable Long projectId) {
        return ResponseEntity.ok(service.getProjectSummary(projectId));
    }
}
