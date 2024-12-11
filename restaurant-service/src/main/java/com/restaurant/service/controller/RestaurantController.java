package com.restaurant.service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.restaurant.service.model.Restaurant;
import com.restaurant.service.model.dto.MenuDTO;
import com.restaurant.service.model.dto.OperatingHoursDTO;
import com.restaurant.service.model.dto.RestaurantDTO;
import com.restaurant.service.model.dto.RestaurantMenuDTO;
import com.restaurant.service.service.RestaurantService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService service;

    @PostMapping
    public ResponseEntity<?> createRestaurant(@RequestBody Restaurant restaurant) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(construcRestaurantDTO(service.createRestaurant(restaurant)));
    }

    private RestaurantDTO construcRestaurantDTO(Restaurant restaurant) {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setId(restaurant.getId());
        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setAddress(restaurant.getAddress());
        restaurantDTO.setEmail(restaurant.getEmail());
        restaurantDTO.setPhoneNo(restaurant.getPhoneNo());
        restaurantDTO.setOperatingHours(new OperatingHoursDTO(restaurant.getOperatingHours().getOpeningTime(), restaurant.getOperatingHours().getClosingTime()));
        return restaurantDTO;
    }

    @PatchMapping("/{restaurantId}")
    public ResponseEntity<?> updateRestaurant(@PathVariable int restaurantId, @RequestBody Restaurant restaurant) {
        return ResponseEntity.status(HttpStatus.OK).body(construcRestaurantDTO(service.updateRestaurant(restaurantId, restaurant)));
    }

    @GetMapping
    public ResponseEntity<?> getAllRestaurants(@RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<RestaurantDTO> restaurantDTOs = service.getAllRestaurants(from, limit).stream()
                .map(restaurant -> construcRestaurantDTO(restaurant)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOs);
        } catch (Exception ex){
            System.out.println(ex);
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<?> getRestaurantById(@PathVariable int restaurantId) {
        return ResponseEntity.status(HttpStatus.OK).body(construcRestaurantDTO(service.getRestaurantById(restaurantId)));
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<?> deleteRestaurantById(@PathVariable int restaurantId) {
        service.deleteRestaurantById(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{restaurantId}/with-menu")
    public ResponseEntity<?> getRestaurantByIdWithMenu(@PathVariable int restaurantId) {
        return ResponseEntity.status(HttpStatus.OK).body(construcRestaurantMenuDTO(service.getRestaurantById(restaurantId)));
    }

    private RestaurantMenuDTO construcRestaurantMenuDTO(Restaurant restaurant) {
        RestaurantMenuDTO restaurantMenuDTO = new RestaurantMenuDTO();
        restaurantMenuDTO.setId(restaurant.getId());
        restaurantMenuDTO.setName(restaurant.getName());
        restaurantMenuDTO.setAddress(restaurant.getAddress());
        restaurantMenuDTO.setEmail(restaurant.getEmail());
        restaurantMenuDTO.setPhoneNo(restaurant.getPhoneNo());
        restaurantMenuDTO.setOperatingHours(new OperatingHoursDTO(restaurant.getOperatingHours().getOpeningTime(), restaurant.getOperatingHours().getClosingTime()));
        
        List<MenuDTO> menuDTOs = restaurant.getMenuItems()
        .stream().map(menu -> new MenuDTO(menu.getId(), menu.getName(), menu.getPrice()))
        .collect(Collectors.toList());
        restaurantMenuDTO.setMenuItems(menuDTOs);

        return restaurantMenuDTO;
    }

}
