package com.gatiya.driver.entity;

import com.gatiya.driver.enums.DriverAvailabilityStatus;
import com.gatiya.driver.enums.DriverVerificationStatus;
import com.gatiya.ride.entity.Ride;
import com.gatiya.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Driver entity class.
 *
 * This entity represents a driver in the system.
 * It extends User entity and adds driver-specific fields.
 */
@Entity
@Table(
        name = "drivers",
        indexes = {
                @Index(name = "idx_drivers_availability_status", columnList = "availability_status"),
                @Index(name = "idx_drivers_verification_status", columnList = "verification_status")
        }
)
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Driver extends User {
    
    @Column(name = "license_number", nullable = false, unique = true, length = 50)
    private String licenseNumber;
    
    @Column(name = "license_expiry_date", nullable = false)
    private LocalDate licenseExpiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false, length = 30)
    private DriverVerificationStatus verificationStatus; // PENDING, DOCUMENTS_UPLOADED, VERIFIED, REJECTED

    @Enumerated(EnumType.STRING)
    @Column(name = "availability_status", nullable = false, length = 20)
    private DriverAvailabilityStatus availabilityStatus; // ONLINE, OFFLINE, ON_RIDE, BUSY
    
    // Last known location (synced from Firebase periodically)
    @Column(name = "last_known_latitude", precision = 10, scale = 7)
    private BigDecimal lastKnownLatitude;
    
    @Column(name = "last_known_longitude", precision = 10, scale = 7)
    private BigDecimal lastKnownLongitude;
    
    @Column(name = "last_location_updated_at")
    private LocalDateTime lastLocationUpdatedAt;
    
    @Column(name = "wallet_balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal walletBalance = BigDecimal.ZERO;
    
    @Column(name = "total_rides", nullable = false)
    private Integer totalRides = 0;
    
    @Column(name = "average_rating", nullable = false, precision = 3, scale = 2)
    private BigDecimal averageRating = BigDecimal.ZERO;
    
    @Column(name = "total_ratings", nullable = false)
    private Integer totalRatings = 0;
    
    @Column(name = "total_earnings", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalEarnings = BigDecimal.ZERO;
    
    @Column(name = "fcm_token", length = 500)
    private String fcmToken; // Push notifications

    // ************* Relationships *************

    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Vehicle vehicle;
    
    @OneToMany(mappedBy = "driver", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<DriverDocument> documents = new ArrayList<>();
    
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    private List<Ride> rides = new ArrayList<>();
}