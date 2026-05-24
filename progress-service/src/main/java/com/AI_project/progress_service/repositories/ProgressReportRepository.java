package com.AI_project.progress_service.repositories;

import com.AI_project.progress_service.models.ProgressReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProgressReportRepository extends JpaRepository<ProgressReport, Long> {
    List<ProgressReport> findByProjectId(Long projectId);
}
