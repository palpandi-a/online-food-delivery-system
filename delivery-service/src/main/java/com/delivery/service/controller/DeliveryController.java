package com.delivery.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.service.service.DeliveryService;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    private final DeliveryService service;

    public DeliveryController(DeliveryService service) {
        this.service = service;
    }

    @GetMapping("/{deliveryId}")
    public ResponseEntity<?> getDeliveryDetailsById(@PathVariable int deliveryId) {
        return ResponseEntity.ok().body(service.getDeliveryDetailsById(deliveryId));
    }

    @GetMapping
    public ResponseEntity<?> getAllDeliveries(@RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok().body(service.getAllDeliveries(from, limit));
    }

}
