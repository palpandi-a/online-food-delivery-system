package com.delivery.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.delivery.service.model.DeliveryDetails;

@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryDetails, Integer> {
    
}
