package com.campushub.backend.dtos.cartItem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CartItemRequestDTO {

    @NotNull(message = "Listing Id is required")
    private UUID listingId;

    @Min(value = 1, message = "Quantity must be at least 1")
    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @Min(value = 1, message = "Unit price must be positive")
    private BigDecimal unitPrice;

    @NotNull(message = "Cart id is required")
    private UUID cartId;
}
