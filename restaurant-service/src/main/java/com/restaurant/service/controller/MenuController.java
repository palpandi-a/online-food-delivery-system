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
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.service.model.Menu;
import com.restaurant.service.model.dto.MenuDTO;
import com.restaurant.service.service.MenuService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping
    public ResponseEntity<?> addMenu(@PathVariable int restaurantId, @RequestBody List<MenuDTO> menuDTOList) {
        List<Menu> menuList = menuService.addMenuItems(restaurantId, menuDTOList);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertMenuAsMenuDTO(menuList));
    }

    private List<MenuDTO> convertMenuAsMenuDTO(List<Menu> menuList) {
        return menuList.stream()
                .map(menu -> convertMenuAsMenuDTO(menu))
                .collect(Collectors.toList());
    }

    private MenuDTO convertMenuAsMenuDTO(Menu menu) {
        return new MenuDTO(menu.getId(), menu.getName(), menu.getPrice());
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<?> getMenu(@PathVariable int restaurantId, @PathVariable int menuId) {
        return ResponseEntity.status(HttpStatus.OK).body(convertMenuAsMenuDTO(menuService.getMenu(menuId)));
    }

    @GetMapping
    public ResponseEntity<?> getAllMenus(@PathVariable int restaurantId) {
        return ResponseEntity.status(HttpStatus.OK).body(convertMenuAsMenuDTO(menuService.getAllMenus(restaurantId)));
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<?> deleteMenu(@PathVariable int restaurantId, @PathVariable int menuId) {
        menuService.deleteMenu(restaurantId, menuId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<?> updateMenu(@PathVariable int restaurantId, @PathVariable int menuId,
            @RequestBody MenuDTO menuDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(convertMenuAsMenuDTO(menuService.updatMenu(restaurantId, menuId, menuDTO)));
    }

}
