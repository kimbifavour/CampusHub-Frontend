package com.campushub.backend.exceptions.listing;

public class ListingNotAvailableException extends RuntimeException {
    public ListingNotAvailableException(String message) {
        super(message);
    }
}
