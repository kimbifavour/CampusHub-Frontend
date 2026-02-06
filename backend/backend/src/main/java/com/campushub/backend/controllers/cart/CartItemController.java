package com.campushub.backend.controllers.cart;

import com.campushub.backend.dtos.cartItem.CartItemRequestDTO;
import com.campushub.backend.dtos.cartItem.CartItemResponseDTO;
import com.campushub.backend.models.cart.CartItem;
import com.campushub.backend.services.cart.CartItemService;
import com.campushub.backend.services.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.togglz.core.manager.FeatureManager;

import java.util.UUID;

import static com.campushub.backend.configurations.togglz.Features.CREATE_CART_ITEM;
import static com.campushub.backend.configurations.togglz.Features.DELETE_CART_ITEM;

@RestController
@RequestMapping("/cart-item")
@Tag(name = "Cart Item", description = "Cart Item related operations")
public class CartItemController {
    @Autowired
    CartItemService cartItemService;

    @Autowired
    CartService cartService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FeatureManager featureManager;

    @PostMapping("/create-cart-item")
    @Operation(summary = "Create cart item",
            description = "Adds a cart item into a specific cart using the carts id. Returns the created cart item's details.")
    public ResponseEntity<CartItemResponseDTO> createCartItem(@RequestBody CartItemRequestDTO cartItemRequestDTO) {
        if (!featureManager.isActive(CREATE_CART_ITEM)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        CartItem cartItem = modelMapper.map(cartItemRequestDTO, CartItem.class);
        cartItem.setCart(cartService.findCartById(cartItemRequestDTO.getCartId()));
        CartItem createdCartItem = cartItemService.createCartItem(cartItem);
        CartItemResponseDTO cartItemResponseDTO = modelMapper.map(createdCartItem, CartItemResponseDTO.class);
        return new ResponseEntity<>(cartItemResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete-cart-item/{cartItemId}")
    @Operation(summary = "Delete Cart Item",
             description = "Removes a cart item from a cart using its UUID. Returns the deleted cart item's details.")
    public ResponseEntity<CartItemResponseDTO> deleteCartItem(@PathVariable UUID cartItemId) {
        if (!featureManager.isActive(DELETE_CART_ITEM)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        CartItem cartItem = cartItemService.deleteCartItem(cartItemId);
        CartItemResponseDTO cartItemResponseDTO = modelMapper.map(cartItem, CartItemResponseDTO.class);
        return new ResponseEntity<>(cartItemResponseDTO, HttpStatus.OK);
    }
}
