# Entities & Relationships

### Common

```java
// ***************** BaseEntity *****************
package com.gatiya.common.entity;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @Column(name = "deleted_at")
    @DeleteTimestamp
    private LocalDateTime deletedAt;
}
```

### User

```java
// ***************** User *****************
package com.gatiya.user.entity;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_users_phone", columnList = "phone_number", unique = true),
                @Index(name = "idx_users_email", columnList = "email", unique = true),
                @Index(name = "idx_users_firebase_uid", columnList = "firebase_uid", unique = true),
                @Index(name = "idx_users_role_status", columnList = "role, status"),
                @Index(name = "idx_users_role_created_at", columnList = "role, created_at DESC")
        }
)
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "phone_number", nullable = false, unique = true, length = 15)
    private String phoneNumber;

    @Column(unique = true, length = 100)
    private String email;

    /**
     * Roles: Rider, Driver, Admin
     * User can register himself as Rider or Driver, and Admin is created by the system.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;

    /**
     * Account Status: ACTIVE, INACTIVE, SUSPENDED, PENDING_VERIFICATION, BANNED
     * Some status are set by the system, and some are set by the admin.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AccountStatus status;


    // Profile image url
    @Column(name = "profile_image_url", length = 500)
    private String profileImageUrl;

    // Verification will be done at the time of otp-verification
    @Column(name = "is_phone_verified", nullable = false)
    private Boolean isPhoneVerified = false;

    /**
     * The Firebase UID is assigned to each user who signs up or logs in to the application using
     * Firebase Authentication.
     */
    @Column(name = "firebase_uid", unique = true, length = 128)
    private String firebaseUid;
}
```

```java
// ***************** UserRole *****************
package com.gatiya.user.enums;

public enum UserRole {
    RIDER,
    DRIVER,
    ADMIN
}

// ***************** AccountStatus *****************
package com.gatiya.user.enums;

public enum AccountStatus {
    ACTIVE,
    INACTIVE,
    SUSPENDED,
    PENDING_VERIFICATION,
    BANNED
}
```

### Rider

```java
// ***************** Rider *****************
package com.gatiya.rider.entity;

@Entity
@Table(name = "riders")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rider extends User {

    @Column(name = "wallet_balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal walletBalance = BigDecimal.ZERO;

    @Column(name = "total_rides", nullable = false)
    private Integer totalRides = 0;

    @Column(name = "average_rating", nullable = false, precision = 3, scale = 2)
    private BigDecimal averageRating = BigDecimal.ZERO;

    @Column(name = "total_ratings", nullable = false)
    private Integer totalRatings = 0;

    // ********************** Relationships **********************

    @OneToMany(mappedBy = "rider", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SavedAddress> savedAddresses = new ArrayList<>();

    @OneToMany(mappedBy = "rider", fetch = FetchType.LAZY)
    private List<Ride> rides = new ArrayList<>();

    @OneToMany(mappedBy = "rider", fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();
}
```

```java
// ***************** SavedAddress *****************
package com.gatiya.rider.entity;

@Entity
@Table(
        name = "saved_addresses",
        indexes = {
                @Index(name = "idx_saved_addresses_rider_id", columnList = "rider_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedAddress extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id", nullable = false)
    private Rider rider;

    @Column(nullable = false, length = 50)
    private String label; // eg. "Home", "Work", "Gym" etc

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude; // eg. 22.5726 (Kolkata)
    
    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude; // eg. 88.3639 (Kolkata)
    
    @Column(name = "address_line", nullable = false, length = 500)
    private String addressLine; // eg. "123 Park Street, 1st Floor"
    
    @Column(length = 100)
    private String city; // eg. "Kolkata"
    
    @Column(length = 100)
    private String state; // eg. "West Bengal"
    
    @Column(length = 50)
    private String country; // eg. "India"
    
    @Column(length = 20)
    private String pinCode; // eg. "700001"
    
    @Column(length = 200)
    private String landmark; // eg. "Opposite City Mall"
    
    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false; // eg. true (for primary address), false (for other saved addresses)
}
```

### Driver

```java
// ***************** Driver *****************
package com.gatiya.driver.entity;

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
```

```java
// ***************** DriverDocument *****************
package com.gatiya.driver.entity;

@Entity
@Table(
        name = "driver_documents",
        indexes = {
                @Index(name = "idx_driver_documents_driver_id", columnList = "driver_id"),
                @Index(name = "idx_driver_documents_document_type", columnList = "document_type"),
                @Index(name = "idx_driver_documents_verification_status", columnList = "verification_status"),
                @Index(name = "idx_driver_documents_expiry_date", columnList = "expiry_date")
        }
)
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDocument extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false, length = 30)
    private DocumentType documentType; // eg. DRIVING_LICENSE, AADHAR_CARD, PAN_CARD, VEHICLE_RC etc.

    @Column(name = "document_url", nullable = false, length = 500)
    private String documentUrl; // eg. "https://example.com/document.jpg", "https://example.com/document.pdf"

    @Column(name = "document_number", unique = true, length = 50)
    private String documentNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false, length = 20)
    private DocumentVerificationStatus verificationStatus; // eg. PENDING, APPROVED, REJECTED

    @Column(name = "rejection_reason", length = 500)
    private String rejectionReason; // eg. "Invalid document", "Document expired" etc.

    @Column(name = "expiry_date")
    private LocalDate expiryDate; // eg. "2025-12-31"

    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;
}
```

```java
// ***************** Vehicle *****************
package com.gatiya.driver.entity;

@Entity
@Table(
        name = "vehicles",
        indexes = {
                @Index(name = "idx_vehicles_driver_id", columnList = "driver_id"),
                @Index(name = "idx_vehicles_registration_number", columnList = "registration_number")
        }
)
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false, unique = true)
    private Driver driver;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false, length = 20)
    private VehicleType vehicleType; // eg. BIKE, SEDAN, SUV

    @Column(nullable = false, length = 50)
    private String make; // eg. "Honda", "Toyota", "Bajaj", "TVS", "Maruti"

    @Column(nullable = false, length = 50)
    private String model; // eg. "City", "Swift", "Pulsar", "Activa", "Innova"

    @Column(name = "color", length = 30)
    private String color; // eg. "Red", "White", "Black", "Silver", "Blue"

    @Column(name = "registration_number", nullable = false, length = 20, unique = true)
    private String registrationNumber; // eg. "WB02AB1234", "KA01CD5678", "MH03EF9012"

    @Column(name = "registration_year", nullable = false)
    private Integer registrationYear; // eg. 2020, 2021, 2022

    @Column(name = "seating_capacity", nullable = false)
    private Integer seatingCapacity; // eg. 1, 2, 3, 4..

    @Column(name = "vehicle_image_url", length = 500)
    private String vehicleImageUrl;

    @Column(name = "insurance_number", length = 50)
    private String insuranceNumber; // eg. "INS1234567890", "POL9876543210"

    @Column(name = "insurance_expiry")
    private LocalDate insuranceExpiry; // eg. LocalDate.of(2024, 12, 31)

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false; // eg. true (active), false (inactive)
}
```

```java
// ***************** DocumentType *****************
package com.gatiya.driver.enums;

public enum DocumentType {
    // Driving Licence
    DRIVING_LICENSE,

    // Aadhar Card
    AADHAR_CARD,

    // Pan Card
    PAN_CARD,

    // Vehicle Registration Certificate
    VEHICLE_RC,

    // Vehicle Insurance
    VEHICLE_INSURANCE,

    // Profile Photo
    PROFILE_PHOTO
}

// ***************** DocumentVerificationStatus *****************
package com.gatiya.driver.enums;

public enum DocumentVerificationStatus {
    // Verification
    PENDING,

    // Verification approved
    APPROVED,

    // Verification rejected
    REJECTED
}

// ***************** DriverAvailabilityStatus *****************
package com.gatiya.driver.enums;

public enum DriverAvailabilityStatus {

    // Driver is online and available to accept rides.
    AVAILABLE,

    // Driver is offline and not available to accept rides.
    UNAVAILABLE,

    // Driver is currently carrying a passenger and not available to accept new rides.
    OCCUPIED,

    // Driver is currently busy and not available to accept new rides.
    BUSY
}

// ***************** DriverVerificationStatus *****************
package com.gatiya.driver.enums;

public enum DriverVerificationStatus {
    // Driver verification is pending, i.e. the driver has not uploaded any documents yet.
    PENDING,

    // Driver has uploaded their documents, but they have not been verified yet.
    DOCUMENTS_UPLOADED,

    // Driver has been verified, i.e. the documents have been verified and the driver is eligible to ride.
    VERIFIED,

    // Driver verification has been rejected due to some reason, e.g. invalid documents, expired documents, etc.
    REJECTED
}

// ***************** VehicleType *****************
package com.gatiya.driver.enums;

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
```

### Ride

```java
// ***************** Ride *****************
package com.gatiya.ride.entity;

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
```

```java
// ***************** RideOffer *****************
package com.gatiya.ride.entity;

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
```

```java
// ***************** CancellationBy *****************
package com.gatiya.ride.enums;

public enum CancellationBy {
    // Ride cancelled by rider
    RIDER,

    // Ride cancelled by driver
    DRIVER,

    // Ride cancelled by system
    SYSTEM,

    // Ride cancelled by admin
    ADMIN
}

// ***************** OfferStatus *****************
package com.gatiya.ride.enums;

/**
 * Note: The ride offer feature is not implemented in version 1.0.0 of the application.
 */
public enum OfferStatus {
    // The offer is pending and has not been accepted or rejected yet
    PENDING,

    // The offer has been accepted by the rider
    ACCEPTED,

    // The offer has been rejected by the rider
    REJECTED,

    // The offer has expired and is no longer valid
    EXPIRED,

    // The offer has been withdrawn by the driver
    WITHDRAWN
}

// ***************** PricingMode *****************
package com.gatiya.ride.enums;

public enum PricingMode {
    // OLA, inDrive style - drivers bid
    // BIDDING,

    // Uber Style.
    // FIXED

    /**
     * Initially we will start with Distance based pricing. It's simple, fairer.
     * To calculate the fare, we will use the following formula: Fare = Base Fare + (Rate per km × Distance)
     */
    DISTANCE_BASED
}

// ***************** RideStatus *****************
package com.gatiya.ride.enums;

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
```

### Payment

```java
// ***************** Payment *****************
package com.gatiya.payment.entity;

@Entity
@Table(
        name = "payments",
        indexes = {
                @Index(name = "idx_payments_rider_id", columnList = "rider_id"),
                @Index(name = "idx_payments_ride_id", columnList = "ride_id"),
                @Index(name = "idx_payments_status", columnList = "status"),
                @Index(name = "idx_payments_payment_id", columnList = "payment_id"),
                @Index(name = "idx_payments_gateway_payment_id", columnList = "gateway_payment_id")
        }
)
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "payment_id", nullable = false, unique = true, length = 100)
    private String paymentId; // Internal payment ID
    
    @Column(name = "gateway_payment_id", unique = true, length = 100)
    private String gatewayPaymentId; // Razorpay payment ID
    
    @Column(name = "gateway_order_id", unique = true, length = 100)
    private String gatewayOrderId; // Razorpay order ID
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_id", nullable = false, unique = true)
    private Ride ride;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id", nullable = false)
    private Rider rider;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    
    @Column(name = "platform_fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal platformFee;
    
    @Column(name = "driver_payout", nullable = false, precision = 10, scale = 2)
    private BigDecimal driverPayout;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false, length = 20)
    private PaymentMethod paymentMethod; // CASH, CARD, UPI, WALLET, NET_BANKING
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status; // PENDING, PROCESSING, SUCCESS, FAILED, REFUNDED, CANCELLED
    
    @Column(name = "gateway_response", length = 2000)
    private String gatewayResponse; // Store raw response for debugging
    
    @Column(name = "failure_reason", length = 500)
    private String failureReason;
    
    @Column(name = "initiated_at", nullable = false)
    private LocalDateTime initiatedAt;
    
    @Column(name = "completed_at")
    private LocalDateTime completedAt;
    
    @Column(name = "refunded_at")
    private LocalDateTime refundedAt;
}
```

```java
// ***************** PaymentMethod *****************
package com.gatiya.payment.enums;

public enum PaymentMethod {
    CASH,
    CARD,
    UPI,
    WALLET,
    NET_BANKING
}

// ***************** PaymentStatus *****************
package com.gatiya.payment.enums;

public enum PaymentStatus {
    PENDING,
    PROCESSING,
    SUCCESS,
    FAILED,
    REFUNDED,
    CANCELLED
}
```

### Rating

```java
// ***************** Rating *****************
package com.gatiya.rating.entity;

@Entity
@Table(
        name = "ratings",
        indexes = {
                @Index(name = "idx_ratings_ride_id", columnList = "ride_id")
        }
)
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rating extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_id", nullable = false, unique = true)
    private Ride ride;
    
    // Rider rates Driver
    @Column(name = "rider_to_driver_rating")
    private Integer riderToDriverRating; // 1-5
    
    @Column(name = "rider_to_driver_comment", length = 500)
    private String riderToDriverComment;

    /**
     * Driver to Rider
     * Note: This feature will not be implemented in the current version.
     */
//    @Column(name = "driver_to_rider_rating")
//    private Integer driverToRiderRating;
//
//    @Column(name = "driver_to_rider_comment", length = 500)
//    private String driverToRiderComment;
}
```

### Notification

```java
// ***************** Notification *****************
package com.gatiya.notification.entity;

@Entity
@Table(
        name = "notifications",
        indexes = {
                @Index(name = "idx_notifications_user_id", columnList = "user_id"),
                @Index(name = "idx_notifications_is_read", columnList = "is_read"),
                @Index(name = "idx_notifications_sent_at", columnList = "sent_at")
        }
)
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private NotificationType type;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;

    @Column(name = "related_ride_id")
    private Long relatedRideId;

    @Column(name = "related_offer_id")
    private Long relatedOfferId;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;
}
```

```java
// ***************** NotificationType *****************
package com.gatiya.notification.enums;

public enum NotificationType {
    RIDE_REQUEST,
    RIDE_ACCEPTED,
    RIDE_CANCELLED,
    DRIVER_ARRIVED,
    RIDE_STARTED,
    RIDE_COMPLETED,
    PAYMENT_SUCCESS,
    PAYMENT_FAILED

    // ***** More notification will be added in the future *****
    /*
    RIDE_OFFER_RECEIVED,
    RATING_RECEIVED,
    ACCOUNT_VERIFIED,
    DOCUMENT_APPROVED,
    DOCUMENT_REJECTED,
    PROMO_CODE_APPLIED,
    WALLET_CREDITED,
    WALLET_DEBITED
    */
}
```

### Location: For Firebase Realtime DB

```java
// ***************** FirebaseDriverLocation *****************
package com.gatiya.location.model;

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
```

```java
// ***************** FirebaseRideOffer *****************
package com.gatiya.location.model;

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
```

```java
// ***************** FirebaseRiderLocation *****************
package com.gatiya.location.model;

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
```

```java
// ***************** FirebaseRideTracking *****************
package com.gatiya.location.model;

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
```