package com.AI_project.task_service.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long projectId;
    private Long assignedTo;
    private String title;
    private String priority; // HIGH, MEDIUM, LOW
    private String status;   // TODO, IN_PROGRESS, COMPLETED
    private int completionPercentage;
}
