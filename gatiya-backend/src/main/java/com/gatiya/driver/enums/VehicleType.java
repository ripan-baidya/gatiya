package com.gatiya.driver.enums;

import lombok.Getter;

/**
 * Enum representing the possible vehicle types.
 */
@Getter
public enum VehicleType {
    BIKE(1),
    SEDAN(4),
    SUV(6);

    // Seating capacity of the vehicle
    private final int capacity;
    
    VehicleType(int capacity) {
        this.capacity = capacity;
    }
}