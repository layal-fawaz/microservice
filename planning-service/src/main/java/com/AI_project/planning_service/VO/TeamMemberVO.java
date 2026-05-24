package com.AI_project.planning_service.VO;

import lombok.Data;

@Data
public class TeamMemberVO {
    private Long id;
    private String name;
    private String skill;
    private boolean isAvailable;
}
