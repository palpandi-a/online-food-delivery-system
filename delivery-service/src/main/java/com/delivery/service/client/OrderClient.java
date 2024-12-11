package com.delivery.service.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PatchExchange;

import com.delivery.service.model.dto.order.OrderDTO;

@HttpExchange
public interface OrderClient {
    
    @PatchExchange("/orders/{orderId}")
    public OrderDTO updateOrderStatus(@PathVariable int orderId, @RequestBody OrderDTO orderDTO);

}
