package com.gatiya.location.model;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FirebaseDriverLocation {

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("vehicleType")
    private String vehicleType; // BIKE, AUTO, MINI, SEDAN, SUV

    @JsonProperty("vehicleModel")
    private String vehicleModel; // e.g., "Honda City"

    @JsonProperty("vehicleColor")
    private String vehicleColor;

    @JsonProperty("vehicleNumber")
    private String vehicleNumber;

    @JsonProperty("availability")
    private String availability; // ONLINE, OFFLINE, ON_RIDE, BUSY

    @JsonProperty("currentRideId")
    private Long currentRideId;

    @JsonProperty("location")
    private LocationData location;

    @JsonProperty("fcmToken")
    private String fcmToken;

    @JsonProperty("rating")
    private Double rating;

    @JsonProperty("totalRides")
    private Integer totalRides;

    @JsonProperty("lastUpdated")
    private Long lastUpdated; // timestamp in milliseconds

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LocationData {

        @JsonProperty("latitude")
        private Double latitude;

        @JsonProperty("longitude")
        private Double longitude;

        @JsonProperty("accuracy")
        private Float accuracy; // GPS accuracy in meters

        @JsonProperty("bearing")
        private Float bearing; // Direction (0-360 degrees)

        @JsonProperty("speed")
        private Float speed; // Speed in m/s

        @JsonProperty("timestamp")
        private Long timestamp; // timestamp in milliseconds
    }
}