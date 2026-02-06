package com.campushub.backend.services.cart;

import com.campushub.backend.models.cart.Cart;
import com.campushub.backend.repositories.cart.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    public Cart createCartTemp() {
        return new Cart();
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }
}
