package com.delivery.service.kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.delivery.service.model.DeliveryDetails;

@Component
public class KafkaMessageProducer {

    private static final Logger LOGGER = Logger.getLogger(KafkaMessageProducer.class.getName());

    private final KafkaTemplate<String, Object> template;

    public KafkaMessageProducer(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    public void publishOrderOutForDeliveryEvent(DeliveryDetails deliveryDetails) {
        publishEvent(deliveryDetails, "order-out-for-delivery", "is out for delivery");
    }

    public void publishOrderDeliveredEvent(DeliveryDetails deliveryDetails) {
        publishEvent(deliveryDetails, "order-delivered", "is delivered");
    }

    private void publishEvent(DeliveryDetails deliveryDetails, String topic, String statusMessage) {
        Map<String, Object> kafkaMessage = constructEventMessageData(deliveryDetails, statusMessage);
        CompletableFuture<SendResult<String, Object>> future = template.send(topic, kafkaMessage);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                LOGGER.log(Level.SEVERE, "Exception while sending order placed message to kafka", ex);
            }
            LOGGER.log(Level.WARNING, "Message has been published");
        });
    }

    private Map<String, Object> constructEventMessageData(DeliveryDetails deliveryDetails, String statusMessage) {
        Map<String, Object> kafkaMessage = new HashMap<>();
        kafkaMessage.put("orderId", deliveryDetails.getOrderId());
        kafkaMessage.put("customerId", deliveryDetails.getCustomerId());
        kafkaMessage.put("status", deliveryDetails.getStatus());

        StringBuilder builder = new StringBuilder();
        builder = builder.append("Your order: #").append(deliveryDetails.getOrderId());
        builder = builder.append(" ").append(statusMessage);

        kafkaMessage.put("message", builder.toString());
        return kafkaMessage;
    }

}
