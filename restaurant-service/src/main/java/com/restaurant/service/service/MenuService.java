package com.restaurant.service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.restaurant.service.exception.ValidationException;
import com.restaurant.service.model.Menu;
import com.restaurant.service.model.Restaurant;
import com.restaurant.service.model.dto.MenuDTO;
import com.restaurant.service.repository.MenuRepository;
import com.restaurant.service.repository.RestaurantRepository;
import com.restaurant.service.validator.MenuValidatorAPI;

@Service
public class MenuService {

    private final RestaurantService restaurantService;

    private final RestaurantRepository restaurantRepository;

    private final MenuRepository menuRepository;

    private final MenuValidatorAPI validatorAPI;

    public MenuService(RestaurantService restaurantService, RestaurantRepository restaurantRepository,
            MenuRepository menuRepository, MenuValidatorAPI validatorAPI) {
        this.restaurantService = restaurantService;
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
        this.validatorAPI = validatorAPI;
    }

    public List<Menu> addMenuItems(int restaurantId, List<MenuDTO> menuDTOList) {
        validatorAPI.validateMenuData(menuDTOList);
        List<Menu> menuList = menuDTOList.stream()
                .map(menuDTO -> constructMenuInstance(restaurantId, menuDTO))
                .collect(Collectors.toList());
        return menuRepository.saveAll(menuList);
    }

    private Menu constructMenuInstance(int restaurantId, MenuDTO menuDTO) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        Menu menu = new Menu();
        menu.setName(menuDTO.name());
        menu.setPrice(menuDTO.price());
        menu.setRestaurant(restaurant);
        return menu;
    }

    public Menu getMenu(int menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new ValidationException("Invalid menuId: " + menuId));
    }

    public List<Menu> getAllMenus(int restaurantId) {
        return menuRepository.findByRestaurant(restaurantService.getRestaurantById(restaurantId));
    }

    public void deleteMenu(int restaurantId, int menuId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        Optional<Menu> menuInfo = restaurant.getMenuItems().stream().filter(menu -> menu.getId() == menuId).findFirst();
        if (!menuInfo.isPresent()) {
            return;
        }
        restaurant.removeMenuItem(menuInfo.get());
        restaurantRepository.save(restaurant);
    }

    public Menu updatMenu(int restaurantId, int menuId, MenuDTO menuDTO) {
        Menu menu = getMenu(menuId);
        if (menuDTO.name() != null && !menuDTO.name().isBlank() && !menuDTO.name().isEmpty()) {
            menu.setName(menuDTO.name());
        }
        menu.setPrice(menuDTO.price());
        return menuRepository.save(menu);
    }

}
