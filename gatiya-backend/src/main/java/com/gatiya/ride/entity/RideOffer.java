package com.gatiya.ride.entity;

/*
import com.gatiya.common.entity.BaseEntity;
import com.gatiya.driver.entity.Driver;
import com.gatiya.ride.enums.OfferStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
*/

/**
 * RideOffer entity class.
 *
 * This entity represents a ride offer made by a driver to a rider.
 * It contains the relevant information about the offer such as the ride, driver, offered fare, status, etc.
 *
 * Note: The ride offer feature is not implemented in version 1.0.0 of the application.
 */
/*
@Entity
@Table(
        name = "ride_offers",
        indexes = {
                @Index(name = "idx_ride_offers_ride_id", columnList = "ride_id"),
                @Index(name = "idx_ride_offers_driver_id", columnList = "driver_id"),
                @Index(name = "idx_ride_offers_status", columnList = "status")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideOffer extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_id", nullable = false)
    private Ride ride;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;
    
    @Column(name = "offered_fare", nullable = false, precision = 10, scale = 2)
    private BigDecimal offeredFare;
    
    @Column(name = "estimated_arrival_time", nullable = false)
    private Integer estimatedArrivalTime; // in minutes
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OfferStatus status; // PENDING, ACCEPTED, REJECTED, EXPIRED, WITHDRAWN
    
    @Column(name = "driver_message", length = 500)
    private String driverMessage; // Optional message from driver
    
    @Column(name = "offered_at", nullable = false)
    private LocalDateTime offeredAt;
    
    @Column(name = "responded_at")
    private LocalDateTime respondedAt;
    
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt; // Offer expires after X minutes
}

*/