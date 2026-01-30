package com.campushub.backend.repositories;

import com.campushub.backend.models.Listing;
import com.campushub.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ListingRepository extends JpaRepository<Listing, UUID> {
//    Optional<Listing> findByUserId(UUID userId);
}
