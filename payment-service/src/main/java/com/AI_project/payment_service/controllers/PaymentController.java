package com.AI_project.payment_service.controllers;

import com.AI_project.payment_service.models.Subscription;
import com.AI_project.payment_service.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping("/subscribe")
    public ResponseEntity<Subscription> subscribe(@RequestBody Subscription sub) {
        return ResponseEntity.ok(service.subscribe(sub));
    }

    @GetMapping
    public List<Subscription> getAll() {
        return service.getAllSubscriptions();
    }

    // Called by idea-service synchronously before AI analysis
    @GetMapping("/validate/{userId}")
    public ResponseEntity<Map<String, Object>> validate(@PathVariable Long userId) {
        return ResponseEntity.ok(service.validateSubscription(userId));
    }

    @PutMapping("/cancel/{userId}")
    public ResponseEntity<Subscription> cancel(@PathVariable Long userId) {
        return ResponseEntity.ok(service.cancelSubscription(userId));
    }
}
