package com.gatiya.driver.entity;

import com.gatiya.common.entity.BaseEntity;
import com.gatiya.driver.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Vehicle entity class
 * It extends BaseEntity and adds vehicle-specific fields.
 */
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