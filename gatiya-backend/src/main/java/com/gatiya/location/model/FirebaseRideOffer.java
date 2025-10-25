package com.gatiya.location.model;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FirebaseRideOffer {
    
    @JsonProperty("offerId")
    private Long offerId;
    
    @JsonProperty("rideId")
    private Long rideId;
    
    @JsonProperty("driverId")
    private Long driverId;
    
    @JsonProperty("driverName")
    private String driverName;
    
    @JsonProperty("driverPhone")
    private String driverPhone;
    
    @JsonProperty("driverRating")
    private Double driverRating;
    
    @JsonProperty("vehicleType")
    private String vehicleType;
    
    @JsonProperty("vehicleModel")
    private String vehicleModel;
    
    @JsonProperty("vehicleNumber")
    private String vehicleNumber;
    
    @JsonProperty("offeredFare")
    private Double offeredFare;
    
    @JsonProperty("estimatedArrivalTime")
    private Integer estimatedArrivalTime; // in minutes
    
    @JsonProperty("driverMessage")
    private String driverMessage;
    
    @JsonProperty("status")
    private String status; // PENDING, ACCEPTED, REJECTED, EXPIRED
    
    @JsonProperty("offeredAt")
    private Long offeredAt;
    
    @JsonProperty("expiresAt")
    private Long expiresAt;
}