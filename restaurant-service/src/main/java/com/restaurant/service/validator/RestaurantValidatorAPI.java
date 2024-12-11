package com.restaurant.service.validator;

import com.restaurant.service.model.OperatingHours;
import com.restaurant.service.model.Restaurant;

public interface RestaurantValidatorAPI {

    void validateRestaurantData(Restaurant restaurant);

    void validateEmailData(String emailId);

    void validatePhoneData(String phoneNo);

    void validateOperatingHoursData(OperatingHours operatingHours);

}
