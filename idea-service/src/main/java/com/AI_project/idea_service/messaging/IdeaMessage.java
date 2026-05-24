package com.AI_project.idea_service.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdeaMessage implements Serializable {
    private Long ideaId;
    private Long userId;
    private String ideaDescription;
    private String category;
}
