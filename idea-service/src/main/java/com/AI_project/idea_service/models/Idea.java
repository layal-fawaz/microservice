package com.AI_project.idea_service.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Idea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String ideaDescription;
    private String category;
    private String status; // PENDING, ANALYZING, ANALYZED
}
