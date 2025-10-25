package com.gatiya.user.enums;

/**
 * Possible account statuses.
 */
public enum AccountStatus {
    // Account is active and user can login.
    ACTIVE,

    // Account is not active and user cannot login.
    INACTIVE,

    // Account was suspended by an administrator due to some reason.
    SUSPENDED,

    // User account is pending verification.
    PENDING_VERIFICATION,

    // Account was banned by an administrator due to some reason.
    BANNED
}