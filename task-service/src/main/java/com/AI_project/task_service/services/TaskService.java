package com.AI_project.task_service.services;

import com.AI_project.task_service.models.Task;
import com.AI_project.task_service.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${progress-service.url}")
    private String progressServiceUrl;

    public Task createTask(Task task) {
        task.setStatus("TODO");
        task.setCompletionPercentage(0);
        return repository.save(task);
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return repository.findById(id);
    }

    public List<Task> getTasksByProject(Long projectId) {
        return repository.findByProjectId(projectId);
    }

    public Task updateTaskStatus(Long id, String newStatus, int completionPercentage) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found: " + id));
        task.setStatus(newStatus);
        task.setCompletionPercentage(completionPercentage);
        Task saved = repository.save(task);

        // Event-driven: notify progress-service (simulated async)
        try {
            Map<String, Object> event = Map.of(
                    "task_id", saved.getId(),
                    "project_id", saved.getProjectId(),
                    "new_status", newStatus,
                    "updated_by", saved.getAssignedTo() != null ? saved.getAssignedTo() : 0L,
                    "completion_percentage", completionPercentage
            );
            restTemplate.postForObject(
                    progressServiceUrl + "/api/v1/progress/update",
                    event, Map.class);
        } catch (Exception e) {
            System.err.println("Progress service notification failed: " + e.getMessage());
        }

        return saved;
    }

    public Task assignTask(Long id, Long memberId) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found: " + id));
        task.setAssignedTo(memberId);
        task.setStatus("IN_PROGRESS");
        return repository.save(task);
    }
}
