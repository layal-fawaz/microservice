package com.AI_project.task_service.controllers;

import com.AI_project.task_service.models.Task;
import com.AI_project.task_service.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(service.createTask(task));
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return service.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) {
        return service.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/project/{projectId}")
    public List<Task> getByProject(@PathVariable Long projectId) {
        return service.getTasksByProject(projectId);
    }

    // PUT /api/v1/tasks/{id}/status  => broadcasts TaskStatusUpdated event to progress-service
    @PutMapping("/{id}/status")
    public ResponseEntity<Task> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        String status = (String) body.get("new_status");
        int pct = body.containsKey("completion_percentage")
                ? (int) body.get("completion_percentage") : 0;
        return ResponseEntity.ok(service.updateTaskStatus(id, status, pct));
    }

    // PUT /api/v1/tasks/{id}/assign
    @PutMapping("/{id}/assign")
    public ResponseEntity<Task> assignTask(
            @PathVariable Long id,
            @RequestBody Map<String, Long> body) {
        return ResponseEntity.ok(service.assignTask(id, body.get("member_id")));
    }
}
