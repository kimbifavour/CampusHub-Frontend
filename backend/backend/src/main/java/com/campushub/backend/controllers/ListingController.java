package com.campushub.backend.controllers;

import com.campushub.backend.dtos.listing.ListingRequestDTO;
import com.campushub.backend.dtos.listing.ListingResponseDTO;
import com.campushub.backend.models.Listing;
import com.campushub.backend.models.User;
import com.campushub.backend.services.ListingService;
import com.campushub.backend.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.togglz.core.manager.FeatureManager;

import java.util.Date;

import static com.campushub.backend.configurations.togglz.Features.CREATE_LISTING;

@RestController
@RequestMapping("/listing") //TODO url check
public class ListingController {

    @Autowired
    ListingService listingService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserService userService;

    @Autowired
    FeatureManager featureManager;

        @PostMapping("/create-listing")
        public ResponseEntity<ListingResponseDTO> createListing(@RequestBody ListingRequestDTO listingRequestDTO) throws Exception {
            if (featureManager.isActive(CREATE_LISTING)) {
                Listing listing = new Listing();
                listing.setTitle(listingRequestDTO.getTitle());
                listing.setDescription(listingRequestDTO.getDescription());
                listing.setPrice(listingRequestDTO.getPrice());
                listing.setCreationDate(listingRequestDTO.getDate() != null ? listingRequestDTO.getDate() : new Date());

                User user = userService.findById(listingRequestDTO.getUserId()).get();

                listing.setUser(user);

                Listing createdListing = listingService.createListing(listing);

                ListingResponseDTO listingResponseDTO = modelMapper.map(createdListing, ListingResponseDTO.class);
                listingResponseDTO.setUserId(createdListing.getUser().getId());

                return new ResponseEntity<>(listingResponseDTO, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(new ListingResponseDTO(), HttpStatus.FORBIDDEN);
            }
        }
}
