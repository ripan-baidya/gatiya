package com.gatiya.ride.entity;

import com.gatiya.common.entity.BaseEntity;
import com.gatiya.driver.entity.Driver;
import com.gatiya.driver.enums.VehicleType;
import com.gatiya.payment.entity.Payment;
import com.gatiya.rating.entity.Rating;
import com.gatiya.ride.enums.CancellationBy;
import com.gatiya.ride.enums.PricingMode;
import com.gatiya.ride.enums.RideStatus;
import com.gatiya.rider.entity.Rider;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Ride entity class.
 * This class represents a ride in the system.
 * It contains all the relevant information about a ride such as the rider, driver, pickup and drop off locations, status, etc.
 * This class is one of the core entities of the application and is used extensively throughout the system.
 */
@Entity
@Table(
        name = "rides",
        indexes = {
                @Index(name = "idx_rides_rider_id", columnList = "rider_id"),
                @Index(name = "idx_rides_driver_id", columnList = "driver_id"),
                @Index(name = "idx_rides_status", columnList = "status"),
                @Index(name = "idx_rides_requested_at", columnList = "requested_at"),
                @Index(name = "idx_rides_ride_code", columnList = "ride_code")
        }
)
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ride extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ride_code", nullable = false, unique = true, length = 50)
    private String rideCode; // e.g., "RIDE-20250101-001"
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id", nullable = false)
    private Rider rider;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private RideStatus status; // eg. SEARCHING_FOR_DRIVER, OFFER_CONFIRMED, DRIVER_ASSIGNED etc.
    
    @Enumerated(EnumType.STRING)
    @Column(name = "requested_vehicle_type", nullable = false, length = 30)
    private VehicleType requestedVehicleType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "pricing_mode", nullable = false, length = 20)
    private PricingMode pricingMode = PricingMode.DISTANCE_BASED; // for MVP
    
    // Pickup Location
    @Column(name = "pickup_latitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal pickupLatitude;
    
    @Column(name = "pickup_longitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal pickupLongitude;
    
    @Column(name = "pickup_address", nullable = false, length = 500)
    private String pickupAddress;

    // Drop Location
    @Column(name = "drop_latitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal dropLatitude;
    
    @Column(name = "drop_longitude", nullable = false, precision = 10, scale = 7)
    private BigDecimal dropLongitude;
    
    @Column(name = "drop_address", nullable = false, length = 500)
    private String dropAddress;

    // Ride Metrics
    @Column(name = "estimated_distance", precision = 10, scale = 2)
    private BigDecimal estimatedDistance; // in km
    
    @Column(name = "estimated_duration")
    private Integer estimatedDuration; // in minutes
    
    @Column(name = "estimated_fare", precision = 10, scale = 2)
    private BigDecimal estimatedFare;
    
    @Column(name = "actual_distance", precision = 10, scale = 2)
    private BigDecimal actualDistance;
    
    @Column(name = "actual_duration")
    private Integer actualDuration;
    
    @Column(name = "final_fare", precision = 10, scale = 2)
    private BigDecimal finalFare;
    
    @Column(name = "platform_commission", precision = 10, scale = 2)
    private BigDecimal platformCommission;
    
    @Column(name = "driver_earnings", precision = 10, scale = 2)
    private BigDecimal driverEarnings;
    
    // OTP for ride verification
    @Column(name = "ride_otp", length = 6)
    private String rideOtp;
    
    // Timestamps
    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;
    
    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;
    
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    
    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;
    
    // Cancellation Info
    @Enumerated(EnumType.STRING)
    @Column(name = "cancelled_by", length = 20)
    private CancellationBy cancelledBy; // RIDER, DRIVER, SYSTEM, ADMIN
    
    @Column(name = "cancellation_reason", length = 500)
    private String cancellationReason;
    
    // Route polyline (optional)
    @Column(name = "route_polyline", length = 5000)
    private String routePolyline; // Encoded polyline from routing API
    
    // ******************** Relationships ********************

    /**
     * Initially we won't work with offers, so it's not required now !!
     * We'll start with distance based pricing, later we will move to bidding.
     */
    // @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    // private List<RideOffer> offers = new ArrayList<>();
    
    @OneToOne(mappedBy = "ride", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Payment payment;
    
    @OneToOne(mappedBy = "ride", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Rating rating;
}