package com.order.service.kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.order.service.model.dto.OrderDTO;

@Component
public class KafkaMessageProducer {

    private static final Logger LOGGER = Logger.getLogger(KafkaMessageProducer.class.getName());

    private final KafkaTemplate<String, Object> template;

    public KafkaMessageProducer(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    public void publishOrderCreatedEvent(OrderDTO orderDTO) {
        Map<String, Object> kafkaMessage = constructEventMessageData(orderDTO);
        CompletableFuture<SendResult<String, Object>> future = template.send("order-placed", kafkaMessage);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                LOGGER.log(Level.WARNING, "Message has been published");
            } else {
                LOGGER.log(Level.SEVERE, "Exception while sending order placed message to kafka", ex);
            }
        });
    }

    private Map<String, Object> constructEventMessageData(OrderDTO orderDTO) {

        Map<String, Object> kafkaMessage = new HashMap<>();
        kafkaMessage.put("orderId", orderDTO.getOrderId());
        kafkaMessage.put("customerId", orderDTO.getCustomerId());
        kafkaMessage.put("status", orderDTO.getStatus());

        StringBuilder builder = new StringBuilder();
        builder = builder.append("Your order: #").append(orderDTO.getOrderId());
        builder = builder.append(" has been placed");

        kafkaMessage.put("message", builder.toString());
        return kafkaMessage;
    }

}
