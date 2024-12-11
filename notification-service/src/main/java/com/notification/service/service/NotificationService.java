package com.notification.service.service;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger LOGGER = Logger.getLogger(NotificationService.class.getName());

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "order-placed", groupId = "delivery-service-group")
    public void handleOrderPlacedEvents(Map<String, Object> kafkaMessage) {
        handleOrderEventsData(kafkaMessage);
    }

    @KafkaListener(topics = "order-out-for-delivery", groupId = "order-out-for-delivery")
    public void handleOrderOutForDeliveryEvents(Map<String, Object> kafkaMessage) {
        handleOrderEventsData(kafkaMessage);
    }

    @KafkaListener(topics = "order-delivered", groupId = "order-delivered")
    public void handleOrderDeliveredEvents(Map<String, Object> kafkaMessage) {
        handleOrderEventsData(kafkaMessage);
    }

    private void handleOrderEventsData(Map<String, Object> kafkaMessage) {
        messagingTemplate.convertAndSend("/queue/notifications", kafkaMessage);
        LOGGER.log(Level.WARNING, "Notification send to user: " + kafkaMessage.getOrDefault("customerId", "{}"));
    }

}
