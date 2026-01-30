package com.campushub.backend.dtos.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    private String username;

    private String email;

    private String password;
}
