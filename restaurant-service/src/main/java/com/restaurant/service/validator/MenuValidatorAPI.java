package com.restaurant.service.validator;

import java.util.List;

import com.restaurant.service.model.dto.MenuDTO;

public interface MenuValidatorAPI {
    
    void validateMenuData(List<MenuDTO> menuList);

}
