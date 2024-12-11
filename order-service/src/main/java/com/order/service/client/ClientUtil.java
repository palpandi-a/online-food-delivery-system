package com.order.service.client;

import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.order.service.exception.ValidationException;
import com.order.service.model.dto.RestaurantDTO;
import com.order.service.model.dto.UserDTO;

@Component
public class ClientUtil {

    private final UserClient userClient;

    private final RestaurantClient restaurantClient;

    public ClientUtil(UserClient userClient, RestaurantClient restaurantClient) {
        this.userClient = userClient;
        this.restaurantClient = restaurantClient;
    }

    public UserDTO findUserById(int customerId) {
        return executeCall(() -> userClient.findUserById(customerId), "Invalid customerId: " + customerId);
    }

    public RestaurantDTO findRestaurantByIdWithMenu(int restaurantId) {
        return executeCall(() -> restaurantClient.findRestaurantByIdWithMenu(restaurantId),
                "Invalid restaurantId: " + restaurantId);
    }

    private <T> T executeCall(Supplier<T> caller, String errorMessage) {
        try {
            return caller.get();
        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new ValidationException(errorMessage);
            }
            throw ex;
        }
    }

}
