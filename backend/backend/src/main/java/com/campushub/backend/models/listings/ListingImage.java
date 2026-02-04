package com.campushub.backend.models.listings;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.sql.Blob;
import java.util.UUID;

@Entity
@Table(name = "listing_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class ListingImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id")
    private UUID imageId;

    private String fileName;

    private String fileType;

    @Lob
    @Column(name = "image")
    private Blob image;

    private String downloadUrl;

    @ManyToOne
    @JoinColumn(name = "listing_id")
    private Listing listing;
}
