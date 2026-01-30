package com.campushub.backend.dtos.listing;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class ListingRequestDTO {

    private String title;

    private String description;

    private BigDecimal price;

    private Date date;

    private UUID userId;
}
