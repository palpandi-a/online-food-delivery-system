package com.delivery.service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.delivery.service.exception.ValidationException;
import com.delivery.service.model.DeliveryDetails;
import com.delivery.service.repository.DeliveryRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class DeliveryService {

    private final DeliveryRepository repository;

    private final EntityManager manager;

    public DeliveryService(DeliveryRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    @SuppressWarnings("unchecked")
    public List<DeliveryDetails> getAllDeliveries(int from, int limit) {
        String queryString = "SELECT * FROM delivery_details LIMIT ?1 OFFSET ?2";
        Query query = manager.createNativeQuery(queryString, DeliveryDetails.class);
        query.setParameter(1, limit);
        query.setParameter(2, from);
        return query.getResultList();
    }

    public DeliveryDetails getDeliveryDetailsById(int deliveryId) {
        return repository.findById(deliveryId)
                .orElseThrow(() -> new ValidationException("Invalid deliveryId: " + deliveryId));
    }

}
