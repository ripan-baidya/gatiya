package com.gatiya.ride.enums;

/**
 * Possible ride statuses.
 */
public enum RideStatus {
    // rider requests a ride
    REQUESTED,

    // when rider requests a ride, it's searching for available drivers
    SEARCHING_FOR_DRIVER,

    // when driver offers to ride, and when rider accept the offer it's confirmed, NOT REQUIRED NOW !!
    // OFFER_CONFIRMED,

    // when driver is assigned to ride
    DRIVER_ASSIGNED,

    // driver is ongoing to pick up the rider
    DRIVER_EN_ROUTE_TO_PICKUP,

    // driver has arrived at pickup location
    DRIVER_ARRIVED_AT_PICKUP,

    // driver starts the ride
    STARTED,

    // ride is ongoing
    ONGOING,

    // ride completed
    COMPLETED,

    // ride cancelled
    CANCELLED
}