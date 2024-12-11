package com.user.service.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.model.User;
import com.user.service.model.UserDTO;
import com.user.service.service.UserServiceAPI;

@RestController
@RequestMapping("/users")
public class UserServiceController {

    @Autowired
    private UserServiceAPI service;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(constructUserDTO(service.createUser(user)));
    }

    private UserDTO constructUserDTO(User user) {
        return new UserDTO(user.getUserId(), user.getName(), user.getEmail(), user.getPhoneNo(), user.getAddress());
    }

    @PatchMapping(value = "/{userId}", consumes = "application/json")
    public ResponseEntity<?> updateUser(@PathVariable int userId, @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(constructUserDTO(service.updateUser(userId, user)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable int userId) {
        return ResponseEntity.status(HttpStatus.OK).body(constructUserDTO(service.getUserById(userId)));
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam(value = "from", defaultValue = "0") int from,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<UserDTO> usersDto = service.getAllUser(from, limit)
                .stream()
                .map(user -> constructUserDTO(user))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(usersDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable int id) {
        service.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

}
