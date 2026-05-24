package com.AI_project.idea_service.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IdeaMessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    public void sendIdeaForAnalysis(IdeaMessage message) {
        System.out.println("Sending idea to RabbitMQ queue: " + message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        System.out.println("Message sent successfully to exchange: " + exchange);
    }
}
