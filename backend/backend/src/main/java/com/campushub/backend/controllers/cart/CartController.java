package com.campushub.backend.controllers.cart;

import com.campushub.backend.dtos.cartItem.CartItemResponseDTO;
import com.campushub.backend.models.cart.Cart;
import com.campushub.backend.models.cart.CartItem;
import com.campushub.backend.services.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.togglz.core.manager.FeatureManager;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.campushub.backend.configurations.togglz.Features.GET_CART_ITEMS;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart", description = "Cart related operations")
public class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FeatureManager featureManager;

    @GetMapping("/get-cart-items/{cartId}")
    @Operation(summary = "Get Cart Items",
            description = "Retrieves all items in the cart by the given cart ID and returns them all in a set.")
    public ResponseEntity<Set<CartItemResponseDTO>> getCartItems(@PathVariable UUID cartId) {

        if (!featureManager.isActive(GET_CART_ITEMS)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Cart cart = cartService.findCartById(cartId);
        Set<CartItem> cartItems = cart.getCartItems();

        Set<CartItemResponseDTO> response = cartItems.stream()
                .map(item -> {
                    CartItemResponseDTO dto = modelMapper.map(item, CartItemResponseDTO.class);
                    dto.setListingId(item.getListing().getListingId());
                    dto.setTotalPrice(
                            item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                    );
                    return dto;
                })
                .collect(Collectors.toSet());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
