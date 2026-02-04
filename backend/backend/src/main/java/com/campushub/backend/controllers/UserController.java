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

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable UUID userId) {
        if (!featureManager.isActive(DELETE_USER)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userService.deleteUserById(userId);
        UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/get-user/{userId}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable UUID userId) {
        if (!featureManager.isActive(GET_USER)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userService.findById(userId);
        UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }
}
