package com.gatiya.payment.entity;

import com.gatiya.common.entity.BaseEntity;
import com.gatiya.payment.enums.PaymentMethod;
import com.gatiya.payment.enums.PaymentStatus;
import com.gatiya.ride.entity.Ride;
import com.gatiya.rider.entity.Rider;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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