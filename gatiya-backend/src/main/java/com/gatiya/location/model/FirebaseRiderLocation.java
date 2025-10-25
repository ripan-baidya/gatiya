package com.gatiya.location.model;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FirebaseRiderLocation {

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("currentRideId")
    private Long currentRideId;

    @JsonProperty("location")
    private LocationData location;

    @JsonProperty("lastUpdated")
    private Long lastUpdated;

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
        private Float accuracy;

        @JsonProperty("timestamp")
        private Long timestamp;
    }
}