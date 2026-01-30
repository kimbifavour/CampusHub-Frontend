package com.campushub.backend.services;

import com.campushub.backend.models.Listing;
import com.campushub.backend.models.User;
import com.campushub.backend.repositories.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ListingService {

    @Autowired
    ListingRepository listingRepository;

    public Listing createListing(Listing listing) {
        return listingRepository.save(listing);
    }

}
