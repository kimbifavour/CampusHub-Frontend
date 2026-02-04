package com.campushub.backend.services.user;

import com.campushub.backend.enums.user.UserStatus;
import com.campushub.backend.models.user.User;
import com.campushub.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User createUser(User user) {
        user.setStatus(UserStatus.PENDING);
        return userRepository.save(user);
    }

    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId);
    }
}
