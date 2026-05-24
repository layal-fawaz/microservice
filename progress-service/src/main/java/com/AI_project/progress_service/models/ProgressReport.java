package com.AI_project.progress_service.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long taskId;
    private Long projectId;
    private String newStatus;
    private Long updatedBy;
    private int completionPercentage;
}
