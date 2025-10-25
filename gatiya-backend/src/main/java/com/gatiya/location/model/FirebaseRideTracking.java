package com.gatiya.location.model;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FirebaseRideTracking {

    @JsonProperty("rideId")
    private Long rideId;

    @JsonProperty("rideCode")
    private String rideCode;

    @JsonProperty("riderId")
    private Long riderId;

    @JsonProperty("riderName")
    private String riderName;

    @JsonProperty("riderPhone")
    private String riderPhone;

    @JsonProperty("driverId")
    private Long driverId;

    @JsonProperty("driverName")
    private String driverName;

    @JsonProperty("driverPhone")
    private String driverPhone;

    @JsonProperty("status")
    private String status; // CONFIRMED, DRIVER_ARRIVED, ONGOING

    @JsonProperty("vehicleType")
    private String vehicleType;

    @JsonProperty("vehicleNumber")
    private String vehicleNumber;

    @JsonProperty("pickup")
    private LocationPoint pickup;

    @JsonProperty("drop")
    private LocationPoint drop;

    @JsonProperty("driverLocation")
    private LocationPoint driverLocation;

    @JsonProperty("estimatedArrival")
    private Integer estimatedArrival; // in minutes

    @JsonProperty("estimatedDistance")
    private Double estimatedDistance; // in km

    @JsonProperty("fare")
    private Double fare;

    @JsonProperty("otp")
    private String otp;

    @JsonProperty("startedAt")
    private Long startedAt; // timestamp

    @JsonProperty("updatedAt")
    private Long updatedAt; // timestamp

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LocationPoint {

        @JsonProperty("latitude")
        private Double latitude;

        @JsonProperty("longitude")
        private Double longitude;

        @JsonProperty("address")
        private String address;

        @JsonProperty("bearing")
        private Float bearing;

        @JsonProperty("timestamp")
        private Long timestamp;
    }
}