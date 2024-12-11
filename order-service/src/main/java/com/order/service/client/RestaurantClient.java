package com.order.service.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import com.order.service.model.dto.RestaurantDTO;

@HttpExchange
public interface RestaurantClient {

    @GetExchange("/restaurants/{restaurantId}/with-menu")
    public RestaurantDTO findRestaurantByIdWithMenu(@PathVariable int restaurantId);

}
