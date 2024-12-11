package com.delivery.service.api;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.delivery.service.client.ClientUtil;
import com.delivery.service.exception.ValidationException;
import com.delivery.service.kafka.KafkaMessageProducer;
import com.delivery.service.model.DeliveryDetails;
import com.delivery.service.model.dto.agent.AgentDTO;
import com.delivery.service.model.dto.agent.AgentOrdersDTO;
import com.delivery.service.model.dto.agent.Status;
import com.delivery.service.model.dto.order.OrderDTO;
import com.delivery.service.repository.DeliveryRepository;

@Component
public class DeliveryAPIImpl implements DeliveryAPI {

    private final ClientUtil clientUtil;

    private final DeliveryRepository deliveryRepository;

    private final KafkaMessageProducer messageProducer;

    public DeliveryAPIImpl(ClientUtil clientUtil, DeliveryRepository deliveryRepository,
            KafkaMessageProducer messageProducer) {
        this.clientUtil = clientUtil;
        this.deliveryRepository = deliveryRepository;
        this.messageProducer = messageProducer;
    }

    @Override
    public void deliver(DeliveryDetails deliveryDetails) {
        AgentDTO availableAgent = clientUtil.findAvailableAgents().stream().findFirst()
                .orElseThrow(() -> new ValidationException("No agents are available to pick and deliver the order"));
        deliveryDetails.setDeliveryAgentId(availableAgent.getAgentId());
        deliveryRepository.save(deliveryDetails);

        updateAgentStatus(availableAgent.getAgentId(), availableAgent, deliveryDetails.getOrderId(), Status.IN_DELIVERY);
        updateOrderStatus(deliveryDetails.getOrderId(), com.delivery.service.model.dto.order.Status.OUT_FOR_DELIVERY);
        deliveryDetails.setStatus(com.delivery.service.model.dto.order.Status.OUT_FOR_DELIVERY.toString());
        messageProducer.publishOrderOutForDeliveryEvent(deliveryDetails);
        deliveryRepository.save(deliveryDetails);

        updateAgentStatus(availableAgent.getAgentId(), availableAgent, null, Status.AVAILABLE);
        updateOrderStatus(deliveryDetails.getOrderId(), com.delivery.service.model.dto.order.Status.DELIVERED);
        deliveryDetails.setStatus(com.delivery.service.model.dto.order.Status.DELIVERED.toString());
        messageProducer.publishOrderDeliveredEvent(deliveryDetails);
        deliveryRepository.save(deliveryDetails);
    }

    private AgentDTO updateAgentStatus(int agentId, AgentDTO agentDTO, Integer orderId,
            com.delivery.service.model.dto.agent.Status agentStatus) {
        agentDTO.setStatus(agentStatus);
        if (orderId != null) {
            AgentOrdersDTO orderDTO = new AgentOrdersDTO();
            orderDTO.setOrderId(orderId);
            agentDTO.getDeliveredOrders().clear();
            agentDTO.getDeliveredOrders().add(orderDTO);
        }
        return clientUtil.updateAgentStatus(agentId, agentDTO);
    }

    private OrderDTO updateOrderStatus(int orderId, com.delivery.service.model.dto.order.Status orderStatus) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setStatus(orderStatus);
        Logger logger = Logger.getLogger(DeliveryAPIImpl.class.getName());
        logger.log(Level.WARNING, "order details: {0}", new Object[] { orderDTO.toString() });
        return clientUtil.updateOrderStatus(orderId, orderDTO);
    }

}
