package com.campushub.backend.dtos.user;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserResponseDTO {

    private UUID id;

    private String username;

    private String email;

    private String password;
}
