package com.gatiya.user.entity;

import com.gatiya.common.entity.BaseEntity;
import com.gatiya.user.enums.AccountStatus;
import com.gatiya.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User entity class.
 *
 * This entity represents a user in the system.
 * It extends BaseEntity and adds user-specific fields. this class will be later extended by Rider and Driver classes.
 */
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