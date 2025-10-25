package com.gatiya.driver.enums;

/**
 * Enum representing the possible driver availability statuses.
 */
public enum DriverAvailabilityStatus {

    // Driver is online and available to accept rides.
    AVAILABLE,

    // Driver is offline and not available to accept rides.
    UNAVAILABLE,

    // Driver is currently carrying a passenger and not available to accept new rides.
    OCCUPIED,

    // Driver is currently busy and not available to accept new rides.
    BUSY
}