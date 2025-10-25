package com.gatiya.driver.entity;

import com.gatiya.common.entity.BaseEntity;
import com.gatiya.driver.enums.DocumentType;
import com.gatiya.driver.enums.DocumentVerificationStatus;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DriverDocument entity class.
 * This class Contains all fields related to driver documents.
 */
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