package com.agent.service.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.agent.service.constants.Constants;
import com.agent.service.exception.ValidationException;
import com.agent.service.model.Agent;
import com.agent.service.model.Orders;
import com.agent.service.model.Status;
import com.agent.service.repository.AgentRepository;
import com.agent.service.validator.ValidatorAPI;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class AgentService {

    private final AgentRepository repository;

    private final EntityManager manager;

    private final ValidatorAPI validatorAPI;

    public AgentService(AgentRepository repository, EntityManager manager, ValidatorAPI validatorAPI) {
        this.repository = repository;
        this.manager = manager;
        this.validatorAPI = validatorAPI;
    }

    public Agent createAgent(Agent agent) {
        validatorAPI.validateAgentData(agent);
        return repository.save(agent);
    }

    public Agent getAgent(int agentId) {
        return repository.findById(agentId)
                .orElseThrow(() -> new ValidationException("Invalid agentId: " + agentId));
    }

    @SuppressWarnings("unchecked")
    public List<Agent> getAllAgents(int from, int limit) {
        String queryString = "SELECT * FROM " + Constants.TABLE_NAME + " LIMIT ?1 OFFSET ?2";
        Query query = manager.createNativeQuery(queryString, Agent.class);
        query.setParameter(1, limit);
        query.setParameter(2, from);
        return query.getResultList();
    }

    public void deleteAnAgent(int agentId) {
        repository.deleteById(agentId);
    }

    public Agent updateAgent(int agentId, Agent agent) {
        Agent existingAgent = getAgent(agentId);
        if (agent.getName() != null && !agent.getName().isEmpty() && !agent.getName().isBlank()) {
            existingAgent.setName(agent.getName());
        }
        if (agent.getStatus() != null) {
            existingAgent.setStatus(agent.getStatus());
        }
        if (agent.getPhoneNo() != null && !agent.getPhoneNo().isEmpty() && !agent.getPhoneNo().isBlank()) {
            if (repository.findByPhoneNo(agent.getPhoneNo()) != null) {
                throw new ValidationException("Phone no already exists: " + agent.getPhoneNo());
            }
            existingAgent.setPhoneNo(agent.getPhoneNo());
        }
        if (agent.getDeliveredOrders() != null) {
            Set<Integer> deliveredOrders = existingAgent.getDeliveredOrders().stream()
            .map(order -> order.getOrderId()).collect(Collectors.toSet());

            List<Orders> existingOrders = existingAgent.getDeliveredOrders();
            for (Orders order : agent.getDeliveredOrders()) {
                if (!deliveredOrders.contains(order.getOrderId())) {
                    existingOrders.add(order);
                }
            }
        }
        return repository.save(existingAgent);
    }

    public List<Agent> getAvailableAgents() {
        return repository.findByStatus(Status.AVAILABLE);
    }

}
