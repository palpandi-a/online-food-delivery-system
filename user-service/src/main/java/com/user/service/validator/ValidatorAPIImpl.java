package com.user.service.validator;

import org.springframework.stereotype.Component;

import com.user.service.constants.Constants;
import com.user.service.exception.ValidationException;
import com.user.service.model.User;
import com.user.service.repository.UserServiceRepository;

@Component
public class ValidatorAPIImpl implements ValidatorAPI {

    private final UserServiceRepository repository;

    public ValidatorAPIImpl(UserServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validateUserData(User user) {
        throwExceptionIfTrue(user == null, Constants.INVALID_PAYLOAD);
        validateName(user.getName());
        validateEmail(user.getEmail());
        validatePhoneNo(user.getPhoneNo());
        validatePassword(user.getPassword());
        validateAddress(user.getAddress());
    }

    private void validateName(String name) {
        throwExceptionIfTrue(name == null, "name is a mandatory field");
        throwExceptionIfTrue(name.isEmpty(), Constants.INVALID_NAME);
    }

    private void validateEmail(String email) {
        throwExceptionIfTrue(email == null, "email is a mandatory field");
        validateEmailData(email);
    }

    private void validatePhoneNo(String phoneNo) {
        throwExceptionIfTrue(phoneNo == null, "phoneNo is a mandatory field");
        validatePhoneData(phoneNo);
    }

    private void validatePassword(String password) {
        throwExceptionIfTrue(password == null, "password is a mandatory field");
        throwExceptionIfTrue(password.isEmpty(), Constants.INVALID_PASSWORD);
    }

    private void validateAddress(String address) {
        throwExceptionIfTrue(address == null, "address is a mandatory field");
        throwExceptionIfTrue(address.isEmpty(), Constants.INVALID_ADDRESS);
    }

    private void throwExceptionIfTrue(boolean value, String message) {
        if (value) {
            throw new ValidationException(message);
        }
    }

    @Override
    public void validateEmailData(String mailId) {
        throwExceptionIfTrue(!mailId.matches(Constants.EMAIL_REGEX), Constants.INVALID_EMAIL);
        throwExceptionIfTrue(repository.findByEmail(mailId) != null, "Email is already taken: " + mailId);
    }

    @Override
    public void validatePhoneData(String phoneNo) {
        throwExceptionIfTrue(!phoneNo.matches(Constants.PHONE_NO_REGEX), Constants.INVALID_PHONE_NO);
        throwExceptionIfTrue(repository.findByPhoneNo(phoneNo) != null, "Phone no is already taken: " + phoneNo);
    }

}
