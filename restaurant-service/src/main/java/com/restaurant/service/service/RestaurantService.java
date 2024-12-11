package com.restaurant.service.service;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.restaurant.service.constants.Constants;
import com.restaurant.service.exception.ValidationException;
import com.restaurant.service.model.Restaurant;
import com.restaurant.service.repository.RestaurantRepository;
import com.restaurant.service.validator.RestaurantValidatorAPI;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class RestaurantService {

    private RestaurantRepository repository;

    private RestaurantValidatorAPI validatorAPI;

    private EntityManager manager;

    public RestaurantService(RestaurantRepository repository, RestaurantValidatorAPI validatorAPI,
            EntityManager manager) {
        this.repository = repository;
        this.validatorAPI = validatorAPI;
        this.manager = manager;
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        validatorAPI.validateRestaurantData(restaurant);
        return repository.save(restaurant);
    }

    public Restaurant getRestaurantById(int restaurantId) {
        return repository.findById(restaurantId)
                .orElseThrow(() -> new ValidationException("Invalid restaurantId: " + restaurantId));
    }

    @SuppressWarnings("unchecked")
    public List<Restaurant> getAllRestaurants(int from, int limit) {
        String queryString = "SELECT * FROM " + Constants.TABLE_NAME + " LIMIT ?1 OFFSET ?2";
        Query query = manager.createNativeQuery(queryString, Restaurant.class);
        query.setParameter(1, limit);
        query.setParameter(2, from);
        return query.getResultList();
    }

    public void deleteRestaurantById(int restaurantId) {
        repository.deleteById(restaurantId);
    }

    private <T> void updateRestaurantField(T newValue, boolean additionalCondition, Consumer<T> setter) {
        if (newValue != null && additionalCondition) {
            setter.accept(newValue);
        }
    }

    public Restaurant updateRestaurant(int restaurantId, Restaurant restaurant) {
        Restaurant existingRestaurant = repository.findById(restaurantId)
                .orElseThrow(() -> new ValidationException("Invalid restaurantId: " + restaurantId));
        updateRestaurantField(restaurant.getName(), !restaurant.getName().isEmpty() && !restaurant.getName().isBlank(),
                existingRestaurant::setName);
        updateRestaurantField(restaurant.getAddress(),
                !restaurant.getAddress().isEmpty() && !restaurant.getAddress().isBlank(),
                existingRestaurant::setAddress);
        updateRestaurantField(restaurant.getMenuItems(), true, existingRestaurant::setMenuItems);
        if (restaurant.getEmail() != null && !restaurant.getEmail().isEmpty() && !restaurant.getEmail().isBlank()) {
            validatorAPI.validateEmailData(restaurant.getEmail());
            existingRestaurant.setEmail(restaurant.getEmail());
        }
        if (restaurant.getPhoneNo() != null && !restaurant.getEmail().isEmpty() && !restaurant.getEmail().isBlank()) {
            validatorAPI.validatePhoneData(restaurant.getPhoneNo());
            existingRestaurant.setPhoneNo(restaurant.getPhoneNo());
        }
        if (restaurant.getOperatingHours() != null) {
            validatorAPI.validateOperatingHoursData(restaurant.getOperatingHours());
            existingRestaurant.setOperatingHours(restaurant.getOperatingHours());
        }
        return repository.save(existingRestaurant);
    }

}
