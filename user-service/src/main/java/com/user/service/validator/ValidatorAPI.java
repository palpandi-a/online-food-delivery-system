package com.user.service.validator;

import com.user.service.model.User;

public interface ValidatorAPI {

    void validateUserData(User user);

    void validateEmailData(String emailId);

    void validatePhoneData(String phoneNo);

}
