package com.delivery.service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delivery_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "order_id", nullable = false)
    private int orderId;
    
    @Column(name = "customer_id", nullable = false)
    private int customerId;

    @Column(name = "delivery_agent_id", nullable = false)
    private int deliveryAgentId;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "status", nullable = false)
    private String status;

}
