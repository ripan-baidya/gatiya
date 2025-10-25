package com.gatiya.rider.entity;

import com.gatiya.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

/**
 * SavedAddress entity class.
 *
 * This entity represents a saved address of a rider.
 * It contains the relevant information about the address such as the rider, label, latitude, longitude,
 * address line, city, state, country, pin code, landmark, and is default.
 */
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