package com.gatiya.ride.enums;

/**
 * Possible pricing modes.
 */
public enum PricingMode {
    // OLA, inDrive style - drivers bid
    // BIDDING,

    // Uber Style.
    // FIXED

    /**
     * Distance based pricing.
     * To calculate the fare, we will use the following formula: Fare = Base Fare + (Rate per km × Distance)
     */
    DISTANCE_BASED
}