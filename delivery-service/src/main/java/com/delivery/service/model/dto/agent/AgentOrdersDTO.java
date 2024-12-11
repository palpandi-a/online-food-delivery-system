package com.delivery.service.model.dto.agent;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AgentOrdersDTO {
    
    private int orderId;
    
}
