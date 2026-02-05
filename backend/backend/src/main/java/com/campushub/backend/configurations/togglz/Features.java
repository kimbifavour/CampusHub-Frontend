package com.campushub.backend.configurations.togglz;

import jdk.jfr.Label;
import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.context.FeatureContext;

public enum Features implements Feature {

    //-----------------------
    // USER APIs
    //-----------------------
    @Label("USER - CreateUser")
    @EnabledByDefault
    CREATE_USER,

    @Label("USER - DeleteUser")
    @EnabledByDefault
    DELETE_USER,

    @Label("USER - GetUserById")
    @EnabledByDefault
    GET_USER_BY_ID,

    @Label("USER - GetUserByUsername")
    @EnabledByDefault
    GET_USER_BY_USERNAME,

    @Label("USER - GetUserByEmail")
    @EnabledByDefault
    GET_USER_BY_EMAIL,

    //-----------------------
    // LISTING APIs
    //-----------------------
    @Label("LISTING - CreateListing")
    @EnabledByDefault
    CREATE_LISTING,

    @Label("LISTING - BuyListing")
    @EnabledByDefault
    BUY_LISTING,

    @Label("LISTING - GetAllListings")
    @EnabledByDefault
    GET_ALL_LISTINGS,

    @Label("LISTING - GetAllListingsByUser")
    @EnabledByDefault
    GET_ALL_LISTINGS_BY_USER,

    @Label("LISTING - GetAllListingsByCategory")
    @EnabledByDefault
    GET_ALL_LISTINGS_BY_CATEGORY,

    @Label("LISTING - DeleteListing")
    @EnabledByDefault
    DELETE_LISTING,

    //-----------------------
    // CATEGORY APIs
    //-----------------------
    @Label("CATEGORY - CreateCategory")
    @EnabledByDefault
    CREATE_CATEGORY,

    @Label("CATEGORY - DeleteCategoryById")
    @EnabledByDefault
    DELETE_CATEGORY_BY_ID,

    @Label("CATEGORY - DeleteCategoryByName")
    @EnabledByDefault
    DELETE_CATEGORY_BY_NAME,

    @Label("CATEGORY - GetAllCategories")
    @EnabledByDefault
    GET_ALL_CATEGORIES,

    //-----------------------
    // LISTINGIMAGE APIs
    //-----------------------
    @Label("LISTINGIMAGE - UploadListingImage")
    @EnabledByDefault
    UPLOAD_LISTING_IMAGE,

    @Label("LISTINGIMAGE - DownloadListingImage")
    @EnabledByDefault
    DOWNLOAD_LISTING_IMAGE,

    @Label("LISTINGIMAGE - GetListingImages")
    @EnabledByDefault
    GET_LISTING_IMAGES,

    @Label("LISTINGIMAGE - DeleteListingImage")
    @EnabledByDefault
    DELETE_LISTING_IMAGE;

    public boolean isActive(){
        return FeatureContext.getFeatureManager().isActive(this);
    }
}
