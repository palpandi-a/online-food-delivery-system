package com.order.service.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import com.order.service.model.dto.UserDTO;

@HttpExchange
public interface UserClient {

    @GetExchange("/users/{userId}")
    public UserDTO findUserById(@PathVariable int userId);

}
