package com.campushub.backend.services.listings;

import com.campushub.backend.enums.listings.ListingStatus;
import com.campushub.backend.models.listings.Listing;
import com.campushub.backend.models.user.User;
import com.campushub.backend.repositories.ListingRepository;
import com.campushub.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    @Transactional
    public Listing createListing(Listing listing) throws Exception {
        if (listing.getUser() == null || listing.getUser().getId() == null) {
            throw new Exception("User must be set before creating listing");
        }

        if (listing.getListingStatus() == null) {
            listing.setListingStatus(ListingStatus.PUBLISHED);
        }

        User user = listing.getUser();
        user.addListing(listing);

        try {
            return listingRepository.save(listing);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Listing was modified by another transaction. Please retry."
            );
        }
    }

    @Transactional
    public Listing buyListing(UUID listingId, UUID buyerId) throws Exception{
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new Exception("Listing not found with id: " + listingId));

        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new Exception("Buyer not found with id: " + buyerId));

        if (listing.getListingStatus() != ListingStatus.PUBLISHED) {
            throw new Exception("Listing is not available for purchase");
        }

        if (listing.getUser().getId().equals(buyerId)) {
            throw new Exception("Cannot buy your own listing");
        }

        listing.setBuyer(buyer);
        listing.setListingStatus(ListingStatus.SOLD);

        return listingRepository.save(listing);
    }
}