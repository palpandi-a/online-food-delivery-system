package com.delivery.service.client;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.delivery.service.exception.ValidationException;
import com.delivery.service.model.dto.agent.AgentDTO;
import com.delivery.service.model.dto.order.OrderDTO;

@Component
public class ClientUtil {

    private final AgentClient agentClient;

    private final OrderClient orderClient;

    public ClientUtil(AgentClient agentClient, OrderClient orderClient) {
        this.agentClient = agentClient;
        this.orderClient = orderClient;
    }

    public List<AgentDTO> findAvailableAgents() {
        return executeCall(() -> agentClient.findAvailableAgents(), "Exception occur while fetching available agents");
    }

    public AgentDTO updateAgentStatus(int agentId, AgentDTO agentDTO) {
        return executeCall(() -> agentClient.updateAgentStatus(agentId, agentDTO), "Invalid agentId: " + agentId);
    }

    public OrderDTO updateOrderStatus(int orderId, OrderDTO orderDTO) {
        return executeCall(() -> orderClient.updateOrderStatus(orderId, orderDTO), "Invalid orderId: " + orderId);
    }

    private <T> T executeCall(Supplier<T> caller, String errorMessage) {
        try {
            return caller.get();
        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode() == HttpStatus.BAD_REQUEST || ex.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
                throw new ValidationException(errorMessage);
            }
            throw ex;
        }
    }

}
