package com.AI_project.payment_service.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String tier;      // FREE, PRO, ENTERPRISE
    private boolean isValid;
    private String transactionId;
}
