package com.campushub.backend.enums.user;

public enum UserStatus {
    ACTIVE,        // normal user
    SUSPENDED,     // temporarily blocked (rules violation)
    BANNED,        // permanently blocked
    PENDING,       // registered but not verified (email/phone)
    DELETED        // soft-deleted account
}
