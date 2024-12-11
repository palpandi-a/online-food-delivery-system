package com.delivery.service.model.dto.agent;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AgentDTO {

    private int agentId;

    private Status status;

    private List<AgentOrdersDTO> deliveredOrders;

}
