package com.campushub.backend.dtos.listing;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class ListingResponseDTO {

    private UUID id;

    private String title;

    private String description;

    private BigDecimal price;

    private Date date;

    private String category;

    private UUID userId;
}
