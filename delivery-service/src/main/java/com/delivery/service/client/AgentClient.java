package com.delivery.service.client;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PatchExchange;

import com.delivery.service.model.dto.agent.AgentDTO;

@HttpExchange
public interface AgentClient {
    
    @GetExchange("/agents/available")
    public List<AgentDTO> findAvailableAgents();

    @PatchExchange("/agents/{agentId}")
    public AgentDTO updateAgentStatus(@PathVariable int agentId, @RequestBody AgentDTO agentDTO);

}
