package com.order.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.service.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByCustomerId(int customerId);

}
