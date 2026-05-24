package com.AI_project.planning_service.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ideaDescription;
    private String category;
    private int estimatedWeeks;
    private Long memberId;
    private Long teamMember;
}
