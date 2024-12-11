package com.delivery.service.kafka;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.delivery.service.api.DeliveryAPI;
import com.delivery.service.model.DeliveryDetails;
import com.delivery.service.repository.DeliveryRepository;

@Component
public class KafkaMessageConsumer { 

    private static final Logger LOGGER = Logger.getLogger(KafkaMessageConsumer.class.getName());

    private final DeliveryRepository repository;

    private final DeliveryAPI deliveryAPI;

    public KafkaMessageConsumer(DeliveryRepository repository, DeliveryAPI deliveryAPI) {
        this.repository = repository;
        this.deliveryAPI = deliveryAPI;
    }
    
    @KafkaListener(topics = "order-placed", groupId = "order-placed")
    public void consumerMessages(Map<String, Object> kafkaMessage) {
        try {
            LOGGER.log(Level.WARNING, "The placed order details: " + kafkaMessage);
            DeliveryDetails deliveryDetails = construcDeliveryDetailsObject(kafkaMessage);
            repository.save(deliveryDetails);
            deliveryAPI.deliver(deliveryDetails);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Exception occur while consuming the message", ex);
            return;
        }
    }

    private DeliveryDetails construcDeliveryDetailsObject(Map<String, Object> kafkaMessage) {
        DeliveryDetails deliveryDetails = new DeliveryDetails();
        deliveryDetails.setOrderId((int) kafkaMessage.get("orderId"));
        deliveryDetails.setCustomerId((int) kafkaMessage.get("customerId"));
        deliveryDetails.setMessage((String) kafkaMessage.get("message"));
        deliveryDetails.setStatus((String) kafkaMessage.get("status"));
        return deliveryDetails;
    }

}
