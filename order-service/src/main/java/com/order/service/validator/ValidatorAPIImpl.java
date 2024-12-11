package com.order.service.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.order.service.exception.ValidationException;
import com.order.service.model.Order;
import com.order.service.model.OrderedItems;
import com.order.service.model.dto.MenuDTO;
import com.order.service.model.dto.RestaurantDTO;

@Component
public class ValidatorAPIImpl {

    public void validateOrderData(Order order) {
        throwExceptionIfTrue(order.getItems() == null || order.getItems().isEmpty(), "Invalid items");
        throwExceptionIfTrue(order.getDeliveryAddress() == null || order.getDeliveryAddress().isEmpty()
                || order.getDeliveryAddress().isBlank(), "Invalid deliveryAddress");
    }

    public void validateRestaurantMenuData(Order order, RestaurantDTO restaurantDTO) {
        List<Integer> orderedItemsIds = order.getItems().stream().map(OrderedItems::getItemId)
                .collect(Collectors.toList());
        List<Integer> menuItemsIds = restaurantDTO.getMenuItems().stream().map(MenuDTO::id)
                .collect(Collectors.toList());
        List<Integer> invalidMenuIds = new ArrayList<>(orderedItemsIds);
        invalidMenuIds.removeAll(menuItemsIds);
        throwExceptionIfTrue(!invalidMenuIds.isEmpty(), "Invalid itemIds: " + invalidMenuIds);
    }

    private void throwExceptionIfTrue(boolean value, String message) {
        if (value) {
            throw new ValidationException(message);
        }
    }

}
