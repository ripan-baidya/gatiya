package com.gatiya.rider.entity;

import com.gatiya.payment.entity.Payment;
import com.gatiya.ride.entity.Ride;
import com.gatiya.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Rider entity class.
 *
 * This entity represents a rider in the system.
 * It extends User entity and adds rider-specific fields.
 */
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
