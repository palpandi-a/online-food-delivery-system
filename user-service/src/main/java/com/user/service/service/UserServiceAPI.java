package com.user.service.service;

import java.util.List;

import com.user.service.model.User;

public interface UserServiceAPI {

    User createUser(User user);

    User updateUser(int userId, User user);

    User getUserById(int userId);

    List<User> getAllUser(int from, int limit);

    void deleteUserById(int userId);

}
