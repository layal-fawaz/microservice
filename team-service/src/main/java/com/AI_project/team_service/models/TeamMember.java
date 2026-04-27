package com.AI_project.team_service.models;
import jakarta.persistence.*;
import lombok.*;

    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class TeamMember {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String skill;
        private boolean isAvailable;
    }
