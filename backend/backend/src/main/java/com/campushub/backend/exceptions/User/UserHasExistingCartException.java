package com.campushub.backend.exceptions.User;

public class UserHasExistingCartException extends RuntimeException {
    public UserHasExistingCartException(String message) {
        super(message);
    }
}
