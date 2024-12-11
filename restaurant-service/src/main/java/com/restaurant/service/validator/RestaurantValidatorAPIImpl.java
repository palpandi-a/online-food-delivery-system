package com.restaurant.service.validator;

import org.springframework.stereotype.Component;

import com.restaurant.service.constants.Constants;
import com.restaurant.service.exception.ValidationException;
import com.restaurant.service.model.OperatingHours;
import com.restaurant.service.model.Restaurant;
import com.restaurant.service.repository.RestaurantRepository;

@Component
public class RestaurantValidatorAPIImpl implements RestaurantValidatorAPI {

    private final RestaurantRepository repository;

    public RestaurantValidatorAPIImpl(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validateRestaurantData(Restaurant restaurant) {
        throwExceptionIfTrue(restaurant == null, Constants.INVALID_PAYLOAD);
        validateName(restaurant.getName());
        validateAddress(restaurant.getAddress());
        validateEmail(restaurant.getEmail());
        validatePhoneNo(restaurant.getPhoneNo());
        validateOperatingHours(restaurant.getOperatingHours());
    }

    private void throwExceptionIfTrue(boolean value, String message) {
        if (value) {
            throw new ValidationException(message);
        }
    }

    private void validateName(String name) {
        throwExceptionIfTrue(name == null, "name is a mandatory field");
        throwExceptionIfTrue(name.isEmpty() || name.isBlank(), Constants.INVALID_NAME);
    }

    private void validateAddress(String address) {
        throwExceptionIfTrue(address == null, "address is a mandatory field");
        throwExceptionIfTrue(address.isEmpty() || address.isBlank(), Constants.INVALID_ADDRESS);
    }

    private void validateEmail(String email) {
        throwExceptionIfTrue(email == null, "email is a mandatory field");
        validateEmailData(email);
    }

    private void validatePhoneNo(String phoneNo) {
        throwExceptionIfTrue(phoneNo == null, "phoneNo is a mandatory field");
        validatePhoneData(phoneNo);
    }

    private void validateOperatingHours(OperatingHours operatingHours) {
        throwExceptionIfTrue(operatingHours == null, "operationHours is a mandatory field");
        throwExceptionIfTrue(operatingHours.getOpeningTime() == null, "openingTime is a mandatory field");
        throwExceptionIfTrue(operatingHours.getClosingTime() == null, "closingTime is a mandatory field");
        validateOperatingHoursData(operatingHours);
    }

    @Override
    public void validateEmailData(String emailId) {
        throwExceptionIfTrue(!emailId.matches(Constants.EMAIL_REGEX), Constants.INVALID_EMAIL);
        throwExceptionIfTrue(repository.findByEmail(emailId) != null, "Email is already taken: " + emailId);
    }

    @Override
    public void validatePhoneData(String phoneNo) {
        throwExceptionIfTrue(!phoneNo.matches(Constants.PHONE_NO_REGEX), Constants.INVALID_PHONE_NO);
        throwExceptionIfTrue(repository.findByPhoneNo(phoneNo) != null, "Phone no is already taken: " + phoneNo);
    }

    @Override
    public void validateOperatingHoursData(OperatingHours operatingHours) {
        throwExceptionIfTrue(!operatingHours.getOpeningTime().matches(Constants.TIME_VALIDATE_REGEX),
                "Invalid openingTime value");
        throwExceptionIfTrue(!operatingHours.getClosingTime().matches(Constants.TIME_VALIDATE_REGEX),
                "Invalid closingTime value");
    }

}
