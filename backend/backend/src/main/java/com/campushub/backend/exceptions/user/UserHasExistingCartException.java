package com.campushub.backend.exceptions.user;

public class UserHasExistingCartException extends RuntimeException {
    public UserHasExistingCartException(String message) {
        super(message);
    }
}
