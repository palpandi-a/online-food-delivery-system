package com.order.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.order.service.model.Order;
import com.order.service.model.Status;
import com.order.service.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        order.setStatus(Status.PLACED);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable int orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getOrderById(orderId));
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(@RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllOrders(from, limit));
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<?> getAllTheCustomerOrders(@PathVariable int customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllTheCustomerOrders(customerId));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable int orderId) {
        service.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<?> updateOrder(@PathVariable int orderId, @RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.updateOrder(orderId, order));
    }

}
