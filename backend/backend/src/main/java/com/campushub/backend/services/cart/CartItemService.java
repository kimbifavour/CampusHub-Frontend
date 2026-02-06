package com.campushub.backend.services.cart;

import com.campushub.backend.models.cart.CartItem;
import com.campushub.backend.repositories.cart.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    @Autowired
    CartItemRepository cartItemRepository;

    public CartItem createCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }
}
