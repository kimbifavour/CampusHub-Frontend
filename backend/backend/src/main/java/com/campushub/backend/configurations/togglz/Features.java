package com.campushub.backend.configurations.togglz;

import jdk.jfr.Label;
import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.context.FeatureContext;

public enum Features implements Feature {
    //-----------------------
    //USER APIs
    //-----------------------
    @Label("CreateUser")
    @EnabledByDefault
    CREATE_USER,

    @Label("DeleteUser")
    @EnabledByDefault
    DELETE_USER,

    @Label("GetUser")
    @EnabledByDefault
    GET_USER,

    //-----------------------
    //Listing APIs
    //-----------------------
    @Label("CreateListing")
    @EnabledByDefault
    CREATE_LISTING,

    @Label("BuyListing")
    @EnabledByDefault
    BUY_LISTING,

    @Label("GetAllListings")
    @EnabledByDefault
    GET_ALL_LISTINGS,

    @Label("GetAllListingsByUser")
    @EnabledByDefault
    GET_ALL_LISTINGS_BY_USER,

    @Label("GetAllListingsByCategory")
    @EnabledByDefault
    GET_ALL_LISTINGS_BY_CATEGORY,

    @Label("DeleteListing")
    @EnabledByDefault
    DELETE_LISTING,

    //-----------------------
    //Category APIs
    //-----------------------
    @Label("CreateCategory")
    @EnabledByDefault
    CREATE_CATEGORY,

    @Label("DeleteCategoryById")
    @EnabledByDefault
    DELETE_CATEGORY_BY_ID,

    @Label("DeleteCategoryByName")
    @EnabledByDefault
    DELETE_CATEGORY_BY_NAME,

    @Label("GetAllCategories")
    @EnabledByDefault
    GET_ALL_CATEGORIES,

    //-----------------------
    //ListingImage APIs
    //-----------------------
    @Label("UploadListingImage")
    @EnabledByDefault
    UPLOAD_LISTING_IMAGE,

    @Label("DownloadListingImage")
    @EnabledByDefault
    DOWNLOAD_LISTING_IMAGE,

    @Label("GetListingImages")
    @EnabledByDefault
    GET_LISTING_IMAGES,

    @Label("DeleteListingImage")
    @EnabledByDefault
    DELETE_LISTING_IMAGE;
    public boolean isActive(){
        return FeatureContext.getFeatureManager().isActive(this);
    }

}