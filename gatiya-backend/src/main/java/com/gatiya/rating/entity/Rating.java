package com.gatiya.rating.entity;

import com.gatiya.common.entity.BaseEntity;
import com.gatiya.ride.entity.Ride;
import jakarta.persistence.*;
import lombok.*;

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