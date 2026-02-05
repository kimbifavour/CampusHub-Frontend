package com.campushub.backend.controllers;

import com.campushub.backend.dtos.listingImage.ListingImageResponseDTO;
import com.campushub.backend.models.listings.ListingImage;
import com.campushub.backend.services.listings.ListingImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.togglz.core.manager.FeatureManager;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.campushub.backend.configurations.togglz.Features.*;

@RestController
@RequestMapping("/listingImage")
public class ListingImageController {

    @Autowired
    ListingImageService listingImageService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FeatureManager featureManager;

    @PostMapping("/upload-listing-image/{listingId}")
    public ResponseEntity<ListingImageResponseDTO> uploadImage(
            @PathVariable UUID listingId,
            @RequestParam("file") MultipartFile file) {
        if (!featureManager.isActive(UPLOAD_LISTING_IMAGE)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            ListingImage image = listingImageService.uploadImage(file, listingId);
            ListingImageResponseDTO listingImageResponseDTO = modelMapper.map(image, ListingImageResponseDTO.class);
            return new ResponseEntity<>(listingImageResponseDTO, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/download-listing-image/{imageId}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable UUID imageId) {
        if (!featureManager.isActive(DOWNLOAD_LISTING_IMAGE)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
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

    @GetMapping("/get-listing-images/{listingId}")
    public ResponseEntity<List<ListingImageResponseDTO>> getListingImages(@PathVariable UUID listingId) {
        if (!featureManager.isActive(GET_LISTING_IMAGES)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<ListingImage> images = listingImageService.getImagesByListing(listingId);
        List<ListingImageResponseDTO> dtos = images.stream()
                .map(image -> modelMapper.map(image, ListingImageResponseDTO.class))
                .toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @DeleteMapping("/delete-listing-image/{imageId}")
    public ResponseEntity<ListingImageResponseDTO> deleteImage(@PathVariable UUID imageId) {
        if (!featureManager.isActive(DELETE_LISTING_IMAGE)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            ListingImage listingImage = listingImageService.deleteImage(imageId);
            ListingImageResponseDTO listingImageResponseDTO = modelMapper.map(listingImage, ListingImageResponseDTO.class);
            return new ResponseEntity<>(listingImageResponseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}