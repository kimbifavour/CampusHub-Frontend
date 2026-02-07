package com.campushub.backend.services.listings;

import com.campushub.backend.models.listings.Listing;
import com.campushub.backend.models.listings.ListingImage;
import com.campushub.backend.repositories.listing.ListingImageRepository;
import com.campushub.backend.repositories.listing.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ListingImageService {

    @Autowired
    private ListingImageRepository listingImageRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Transactional
    public ListingImage uploadImage(MultipartFile file, UUID listingId) throws IOException {
        // Validate file
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload empty file");
        }

        // Validate file type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        // Validate file size (e.g., max 5MB)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("File size exceeds maximum limit of 5MB");
        }

        // Find listing
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Listing not found with id: " + listingId));

        // Create image entity
        ListingImage image = new ListingImage();
        image.setFileName(file.getOriginalFilename());
        image.setFileType(contentType);
        image.setImageData(file.getBytes());
        image.setFileSize(file.getSize());
        image.setUploadDate(LocalDateTime.now());
        image.setListing(listing);

        return listingImageRepository.save(image);
    }

    @Transactional(readOnly = true)
    public ListingImage getImage(UUID imageId) {
        return listingImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));
    }

    @Transactional(readOnly = true)
    public List<ListingImage> getImagesByListing(UUID listingId) {
        return listingImageRepository.findByListing_ListingId(listingId);
    }

    @Transactional
    public ListingImage deleteImage(UUID imageId) {
        ListingImage image = listingImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));

        listingImageRepository.delete(image);
        return image;
    }

    @Transactional
    public void deleteImagesByListing(UUID listingId) {
        listingImageRepository.deleteByListing_ListingId(listingId);
    }
}