package com.restaurant.service.validator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.restaurant.service.constants.Constants;
import com.restaurant.service.exception.ValidationException;
import com.restaurant.service.model.dto.MenuDTO;

@Component
public class MenuValidatorAPIImpl implements MenuValidatorAPI {

    @Override
    public void validateMenuData(List<MenuDTO> menuList) {
        throwExceptionIfTrue(menuList.isEmpty(), Constants.INVALID_PAYLOAD);
    }

    private void throwExceptionIfTrue(boolean value, String message) {
        if (value) {
            throw new ValidationException(message);
        }
    }
    
}
