package com.campushub.backend.controllers;

import com.campushub.backend.models.listings.ListingImage;
import com.campushub.backend.services.listings.ListingImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/listings")
public class ListingImageController {

    @Autowired
    private ListingImageService listingImageService;

    @PostMapping("/{listingId}/images")
    public ResponseEntity<ListingImage> uploadImage(
            @PathVariable UUID listingId,
            @RequestParam("file") MultipartFile file) {
        try {
            ListingImage image = listingImageService.uploadImage(file, listingId);
            return ResponseEntity.status(HttpStatus.CREATED).body(image);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/images/{imageId}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable UUID imageId) {
        try {
            ListingImage image = listingImageService.getImage(imageId);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + image.getFileName() + "\"")
                    .body(image.getImageData());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{listingId}/images")
    public ResponseEntity<List<ListingImage>> getListingImages(@PathVariable UUID listingId) {
        List<ListingImage> images = listingImageService.getImagesByListing(listingId);
        return ResponseEntity.ok(images);
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable UUID imageId) {
        try {
            listingImageService.deleteImage(imageId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}