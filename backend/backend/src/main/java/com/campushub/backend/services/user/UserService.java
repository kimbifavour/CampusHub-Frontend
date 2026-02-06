package com.campushub.backend.services.user;

import com.campushub.backend.enums.user.UserStatus;
import com.campushub.backend.exceptions.User.UserNotFoundException;
import com.campushub.backend.models.cart.Cart;
import com.campushub.backend.models.user.User;
import com.campushub.backend.repositories.user.UserRepository;
import com.campushub.backend.services.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CartService cartService;

    public User createUser(User user) {
        user.setStatus(UserStatus.PENDING);
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);
        return userRepository.save(user);
    }

    public User findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Transactional
    public User deleteUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        userRepository.deleteById(userId);

        return user;
    }
}
