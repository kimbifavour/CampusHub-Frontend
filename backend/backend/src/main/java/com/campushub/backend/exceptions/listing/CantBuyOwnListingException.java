package com.campushub.backend.exceptions.listing;

public class CantBuyOwnListingException extends RuntimeException {
    public CantBuyOwnListingException(String message) {
        super(message);
    }
}
