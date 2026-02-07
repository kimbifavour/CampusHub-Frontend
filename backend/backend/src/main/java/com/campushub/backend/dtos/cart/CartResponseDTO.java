package com.campushub.backend.dtos.cart;

import com.campushub.backend.dtos.cartItem.CartItemResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class CartResponseDTO {

    private UUID cartId;
    private UUID userId;
    private BigDecimal totalPrice;
    private Integer listingsQuantity;

    private Set<CartItemResponseDTO> cartItems;
}
