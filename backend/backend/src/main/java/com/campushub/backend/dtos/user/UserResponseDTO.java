package com.campushub.backend.dtos.user;

import com.campushub.backend.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private UUID id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private UserStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}