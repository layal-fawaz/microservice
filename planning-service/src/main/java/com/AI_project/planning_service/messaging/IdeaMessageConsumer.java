package com.AI_project.planning_service.messaging;

import com.AI_project.planning_service.models.ProjectPlan;
import com.AI_project.planning_service.repositories.ProjectPlanRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdeaMessageConsumer {

    @Autowired
    private ProjectPlanRepository repository;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void consumeIdeaMessage(IdeaMessage message) {
        System.out.println("Planning service received message from RabbitMQ: " + message);

        // AI analysis simulation: generate plan from idea
        ProjectPlan plan = new ProjectPlan();
        plan.setIdeaDescription(message.getIdeaDescription());
        plan.setCategory(message.getCategory());
        plan.setEstimatedWeeks(estimateWeeks(message.getCategory()));
        plan.setMemberId(message.getUserId());
        plan.setTeamMember(null);

        ProjectPlan saved = repository.save(plan);
        System.out.println("ProjectPlan created from RabbitMQ message: " + saved);
    }

    private int estimateWeeks(String category) {
        if (category == null) return 8;
        return switch (category.toLowerCase()) {
            case "ai platform" -> 12;
            case "mobile app"  -> 10;
            case "web app"     -> 8;
            case "api"         -> 6;
            default            -> 8;
        };
    }
}
