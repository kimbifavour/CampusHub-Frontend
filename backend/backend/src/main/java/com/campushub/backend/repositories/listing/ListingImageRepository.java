package com.campushub.backend.repositories.listing;

import com.campushub.backend.models.listings.ListingImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ListingImageRepository extends JpaRepository<ListingImage, UUID> {

    List<ListingImage> findByListing_ListingId(UUID listingId);

    @Modifying
    @Query("DELETE FROM ListingImage li WHERE li.listing.listingId = :listingId")
    void deleteByListing_ListingId(@Param("listingId") UUID listingId);
}