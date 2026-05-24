package com.AI_project.payment_service.services;

import com.AI_project.payment_service.models.Subscription;
import com.AI_project.payment_service.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;

@Service
public class PaymentService {

    @Autowired
    private SubscriptionRepository repository;

    public Subscription subscribe(Subscription sub) {
        sub.setTransactionId(UUID.randomUUID().toString());
        sub.setValid(true);
        return repository.save(sub);
    }

    public List<Subscription> getAllSubscriptions() {
        return repository.findAll();
    }

    // Called by idea-service (Sync) to validate before AI analysis
    public Map<String, Object> validateSubscription(Long userId) {
        return repository.findByUserId(userId)
                .map(sub -> {
                    Map<String, Object> res = new HashMap<>();
                    res.put("user_id", userId);
                    res.put("transaction_id", sub.getTransactionId());
                    res.put("required_tier", sub.getTier());
                    res.put("is_valid", sub.isValid());
                    res.put("status", sub.isValid() ? "ACTIVE" : "INACTIVE");
                    return res;
                })
                .orElseGet(() -> {
                    Map<String, Object> res = new HashMap<>();
                    res.put("user_id", userId);
                    res.put("is_valid", false);
                    res.put("status", "NO_SUBSCRIPTION");
                    return res;
                });
    }

    public Subscription cancelSubscription(Long userId) {
        Subscription sub = repository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Subscription not found for user: " + userId));
        sub.setValid(false);
        return repository.save(sub);
    }
}
