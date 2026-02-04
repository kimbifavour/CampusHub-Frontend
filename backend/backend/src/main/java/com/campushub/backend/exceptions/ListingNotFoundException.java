package com.campushub.backend.exceptions;

public class ListingNotFoundException extends RuntimeException {

    public ListingNotFoundException(String message) {
        super(message);
    }
}
