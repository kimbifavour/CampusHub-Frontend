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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.togglz.core.manager.FeatureManager;

import static com.campushub.backend.configurations.togglz.Features.CREATE_USER;

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
        if (featureManager.isActive(CREATE_USER)) {
            User user = modelMapper.map(userRequestDTO, User.class);
            User createdUser = userService.createUser(user);
            UserResponseDTO userResponseDTO = modelMapper.map(createdUser, UserResponseDTO.class);
            return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new UserResponseDTO(), HttpStatus.FORBIDDEN);
        }
    }
}
