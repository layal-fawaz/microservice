package com.AI_project.planning_service.services;

import com.AI_project.planning_service.VO.ResponseVO;
import com.AI_project.planning_service.VO.TeamMemberVO;
import com.AI_project.planning_service.models.ProjectPlan;
import com.AI_project.planning_service.repositories.ProjectPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProjectPlanService {

    @Autowired
    private ProjectPlanRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${team-service.url}")
    private String teamServiceUrl;

    public List<ProjectPlan> getAllPlans() {
        return repository.findAll();
    }

    public ProjectPlan addPlan(ProjectPlan plan) {
        return repository.save(plan);
    }

    public ResponseVO getPlanWithTeamDetails(Long id) {
        ProjectPlan plan = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found with id: " + id));

        ResponseVO response = new ResponseVO();
        response.setProjectPlan(plan);

        try {
            TeamMemberVO member = restTemplate.getForObject(
                    teamServiceUrl + "/api/v1/team/" + plan.getMemberId(),
                    TeamMemberVO.class
            );
            response.setTeamMember(member);
        } catch (Exception e) {
            System.err.println("Could not fetch team member: " + e.getMessage());
        }

        return response;
    }
}
