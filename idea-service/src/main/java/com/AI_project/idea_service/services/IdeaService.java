package com.AI_project.idea_service.services;

import com.AI_project.idea_service.messaging.IdeaMessage;
import com.AI_project.idea_service.messaging.IdeaMessageProducer;
import com.AI_project.idea_service.models.Idea;
import com.AI_project.idea_service.repositories.IdeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class IdeaService {

    @Autowired
    private IdeaRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IdeaMessageProducer messageProducer;

    @Value("${payment-service.url}")
    private String paymentServiceUrl;

    public Idea submitIdea(Idea idea) {
        idea.setStatus("PENDING");
        return repository.save(idea);
    }

    public List<Idea> getAllIdeas() {
        return repository.findAll();
    }

    public Optional<Idea> getIdeaById(Long id) {
        return repository.findById(id);
    }

    public List<Idea> getIdeasByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public Map<String, Object> analyzeIdea(Long ideaId) {
        Idea idea = repository.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Idea not found: " + ideaId));

        // Step 1: Validate subscription - Synchronous blocking call to payment-service
        boolean subscriptionValid = false;
        try {
            Map response = restTemplate.getForObject(
                    paymentServiceUrl + "/api/v1/payment/validate/" + idea.getUserId(),
                    Map.class);
            subscriptionValid = response != null && Boolean.TRUE.equals(response.get("is_valid"));
        } catch (Exception e) {
            System.err.println("Payment service unavailable: " + e.getMessage());
            subscriptionValid = true; // fallback for demo
        }

        if (!subscriptionValid) {
            return Map.of("error", "Subscription not valid. Please subscribe first.");
        }

        // Step 2: Update status to ANALYZING
        idea.setStatus("ANALYZING");
        repository.save(idea);

        // Step 3: Send to planning-service via RabbitMQ (Asynchronous non-blocking)
        IdeaMessage message = new IdeaMessage(
                idea.getId(),
                idea.getUserId(),
                idea.getIdeaDescription(),
                idea.getCategory() != null ? idea.getCategory() : "General"
        );
        messageProducer.sendIdeaForAnalysis(message);

        // idea-service is free immediately after publishing - non-blocking!
        return Map.of(
                "idea_id", ideaId,
                "status", "ANALYZING",
                "message", "Idea queued for analysis via RabbitMQ. Planning service will process it asynchronously."
        );
    }
}
