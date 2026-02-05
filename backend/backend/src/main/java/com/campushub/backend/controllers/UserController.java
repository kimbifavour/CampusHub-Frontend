package com.campushub.backend.controllers;

import com.campushub.backend.dtos.user.UserRequestDTO;
import com.campushub.backend.dtos.user.UserResponseDTO;
import com.campushub.backend.models.user.User;
import com.campushub.backend.services.user.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.togglz.core.manager.FeatureManager;

import java.util.UUID;

import static com.campushub.backend.configurations.togglz.Features.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FeatureManager featureManager;

    @PostMapping("/create-user")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) throws Exception{
        if (!featureManager.isActive(CREATE_USER)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = modelMapper.map(userRequestDTO, User.class);
        User createdUser = userService.createUser(user);
        UserResponseDTO userResponseDTO = modelMapper.map(createdUser, UserResponseDTO.class);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable UUID userId) {
        if (!featureManager.isActive(DELETE_USER)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userService.deleteUserById(userId);
        UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/get-user-by-id/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID userId) {
        if (!featureManager.isActive(GET_USER_BY_ID)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userService.findById(userId);
        UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/get-user-by-username/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username) {
        if (!featureManager.isActive(GET_USER_BY_USERNAME)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userService.findByUsername(username);
        UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/get-user-by-email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        if (!featureManager.isActive(GET_USER_BY_EMAIL)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userService.findByEmail(email);
        UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }
}
