package com.AI_project.progress_service.services;

import com.AI_project.progress_service.models.ProgressReport;
import com.AI_project.progress_service.repositories.ProgressReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ProgressService {

    @Autowired
    private ProgressReportRepository repository;

    // Called by task-service when TaskStatusUpdated event fires
    public ProgressReport recordUpdate(Map<String, Object> event) {
        ProgressReport report = new ProgressReport();
        report.setTaskId(Long.valueOf(event.get("task_id").toString()));
        report.setProjectId(Long.valueOf(event.get("project_id").toString()));
        report.setNewStatus(event.get("new_status").toString());
        report.setUpdatedBy(Long.valueOf(event.get("updated_by").toString()));
        report.setCompletionPercentage((int) event.get("completion_percentage"));
        return repository.save(report);
    }

    public List<ProgressReport> getAllReports() {
        return repository.findAll();
    }

    public List<ProgressReport> getReportsByProject(Long projectId) {
        return repository.findByProjectId(projectId);
    }

    public Map<String, Object> getProjectSummary(Long projectId) {
        List<ProgressReport> reports = repository.findByProjectId(projectId);
        if (reports.isEmpty()) return Map.of("projectId", projectId, "averageCompletion", 0);
        double avg = reports.stream()
                .mapToInt(ProgressReport::getCompletionPercentage)
                .average().orElse(0);
        long completed = reports.stream()
                .filter(r -> "COMPLETED".equals(r.getNewStatus())).count();
        return Map.of(
                "projectId", projectId,
                "totalTasks", reports.size(),
                "completedTasks", completed,
                "averageCompletion", Math.round(avg)
        );
    }
}
