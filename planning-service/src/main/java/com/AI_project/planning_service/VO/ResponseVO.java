package com.AI_project.planning_service.VO;

import com.AI_project.planning_service.models.ProjectPlan;
import lombok.Data;

@Data
public class ResponseVO {
    private ProjectPlan projectPlan;
    private TeamMemberVO teamMember;
}
