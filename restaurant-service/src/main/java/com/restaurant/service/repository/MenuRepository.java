package com.restaurant.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.service.model.Menu;
import com.restaurant.service.model.Restaurant;

import java.util.List;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    
    List<Menu> findByRestaurant(Restaurant restaurant);

}
