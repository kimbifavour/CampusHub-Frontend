package com.campushub.backend.dtos.listing;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class ListingRequestDTO {

    @NotBlank
    @Size(min = 2, max = 100)
    private String title;

    @Size(max = 1000)
    private String description;

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 6, fraction = 2)
    private BigDecimal price;

    @NotNull
    private Date date;

    @NotBlank
    private String category;

    @NotNull
    private UUID userId;
}
