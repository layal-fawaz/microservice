package com.AI_project.planning_service.repositories;

import com.AI_project.planning_service.models.ProjectPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectPlanRepository extends JpaRepository<ProjectPlan, Long> {
}
