package com.user.service.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.user.service.constants.Constants;
import com.user.service.exception.ValidationException;
import com.user.service.model.User;
import com.user.service.repository.UserServiceRepository;
import com.user.service.validator.ValidatorAPI;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class UserServiceAPIImpl implements UserServiceAPI {

    private final ValidatorAPI validatorAPI;

    private final UserServiceRepository repository;

    private final EntityManager entityManager;

    public UserServiceAPIImpl(ValidatorAPI validatorAPI, UserServiceRepository repository,
            EntityManager entityManager) {
        this.validatorAPI = validatorAPI;
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Override
    public User createUser(User user) {
        validateUserData(user);
        user.setPassword(hashPassword(user.getPassword()));
        return repository.save(user);
    }

    private void validateUserData(User user) {
        validatorAPI.validateUserData(user);
        validatorAPI.validateEmailData(user.getEmail());
        validatorAPI.validatePhoneData(user.getPhoneNo());
    }

    private String hashPassword(String inputStr) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(inputStr.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error while hashing password", ex);
        }
    }

    @Override
    public User getUserById(int userId) {
        return repository.findById(userId).orElseThrow(() -> new ValidationException("Invalid userId: " + userId));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllUser(int from, int limit) {
        String queryString = "SELECT * FROM " + Constants.TABLE_NAME + " LIMIT ?1 OFFSET ?2";
        Query query = entityManager.createNativeQuery(queryString, User.class);
        query.setParameter(1, limit);
        query.setParameter(2, from);
        return query.getResultList();
    }

    @Override
    public void deleteUserById(int userId) {
        repository.deleteById(userId);
    }

    private void updateUserField(String newValue, Consumer<String> setter) {
        if (newValue != null && !newValue.isEmpty() && !newValue.isBlank()) {
            setter.accept(newValue);
        }
    }

    @Override
    public User updateUser(int userId, User user) {
        User existingUser = getUserById(userId);
        updateUserField(user.getName(), existingUser::setName);
        updateUserField(user.getEmail(), existingUser::setEmail);
        updateUserField(user.getPhoneNo(), existingUser::setPhoneNo);
        updateUserField(user.getAddress(), existingUser::setAddress);
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            updateUserField(hashPassword(user.getPassword()), existingUser::setPassword);
        }
        return repository.save(existingUser);
    }

}
