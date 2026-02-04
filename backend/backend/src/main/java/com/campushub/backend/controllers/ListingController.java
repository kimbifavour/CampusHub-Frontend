package com.campushub.backend.controllers;

import com.campushub.backend.dtos.listing.ListingRequestDTO;
import com.campushub.backend.dtos.listing.ListingResponseDTO;
import com.campushub.backend.enums.listings.ListingStatus;
import com.campushub.backend.models.listings.Category;
import com.campushub.backend.models.listings.Listing;
import com.campushub.backend.models.user.User;
import com.campushub.backend.services.listings.CategoryService;
import com.campushub.backend.services.listings.ListingService;
import com.campushub.backend.services.user.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.togglz.core.manager.FeatureManager;


import java.util.UUID;

import static com.campushub.backend.configurations.togglz.Features.CREATE_LISTING;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    @Autowired
    ListingService listingService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserService userService;

    @Autowired
    FeatureManager featureManager;

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create-listing")
    public ResponseEntity<ListingResponseDTO> createListing(
            @Valid @RequestBody ListingRequestDTO listingRequestDTO) throws Exception {

        if (!featureManager.isActive(CREATE_LISTING)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User user = userService.findById(listingRequestDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));

        Listing listing = new Listing();
        listing.setTitle(listingRequestDTO.getTitle());
        listing.setDescription(listingRequestDTO.getDescription());
        listing.setPrice(listingRequestDTO.getPrice());
        listing.setUser(user);
        String categoryName = listingRequestDTO.getCategoryName();
        Category category = categoryService.findCategoryByName(categoryName);
        if (category == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found: " + categoryName);
        }
        listing.setCategory(category);
        listing.setListingStatus(ListingStatus.PUBLISHED);

        Listing createdListing = listingService.createListing(listing);

        ListingResponseDTO response = modelMapper.map(createdListing, ListingResponseDTO.class);
        response.setListingId(createdListing.getListingId());
        response.setUserId(createdListing.getUser().getId());
        response.setBuyerId(
                createdListing.getBuyer() != null
                        ? createdListing.getBuyer().getId()
                        : null
        );
        response.setStatus(createdListing.getListingStatus());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{listingId}/buy")
    public ResponseEntity<ListingResponseDTO> buyListing(
            @PathVariable UUID listingId,
            @RequestParam UUID buyerId) throws Exception {

        Listing listing = listingService.buyListing(listingId, buyerId);

        ListingResponseDTO response =
                modelMapper.map(listing, ListingResponseDTO.class);

        response.setListingId(listing.getListingId());
        response.setUserId(listing.getUser().getId());
        response.setBuyerId(
                listing.getBuyer() != null ? listing.getBuyer().getId() : null
        );
        response.setStatus(listing.getListingStatus());

        return ResponseEntity.ok(response);
    }
}
