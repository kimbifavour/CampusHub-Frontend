package com.campushub.backend.repositories;

import com.campushub.backend.models.listings.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ListingRepository extends JpaRepository<Listing, UUID> {
    List<Listing> findByUserId(UUID userId);
    List<Listing> findByCategoryName(String categoryName);
}
