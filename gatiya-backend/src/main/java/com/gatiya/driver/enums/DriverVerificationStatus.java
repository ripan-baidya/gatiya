package com.gatiya.driver.enums;

/**
 * Enum representing the possible driver verification statuses.
 */
public enum DriverVerificationStatus {
    // Driver verification is pending, i.e. the driver has not uploaded any documents yet.
    PENDING,

    // Driver has uploaded their documents, but they have not been verified yet.
    DOCUMENTS_UPLOADED,

    // Driver has been verified, i.e. the documents have been verified and the driver is eligible to ride.
    VERIFIED,

    // Driver verification has been rejected due to some reason, e.g. invalid documents, expired documents, etc.
    REJECTED
}