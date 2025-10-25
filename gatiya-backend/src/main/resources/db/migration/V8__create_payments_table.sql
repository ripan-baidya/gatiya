CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    payment_id VARCHAR(100) NOT NULL UNIQUE,
    gateway_payment_id VARCHAR(100) UNIQUE,
    gateway_order_id VARCHAR(100) UNIQUE,
    ride_id BIGINT NOT NULL UNIQUE,
    rider_id BIGINT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    platform_fee DECIMAL(10, 2) NOT NULL,
    driver_payout DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(20) NOT NULL CHECK (payment_method IN ('CASH', 'CARD', 'UPI', 'WALLET', 'NET_BANKING')),
    status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'PROCESSING', 'SUCCESS', 'FAILED', 'REFUNDED', 'CANCELLED')),
    gateway_response VARCHAR(2000),
    failure_reason VARCHAR(500),
    initiated_at TIMESTAMP NOT NULL,
    completed_at TIMESTAMP,
    refunded_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_payments_ride_id FOREIGN KEY (ride_id) REFERENCES rides(id) ON DELETE RESTRICT,
    CONSTRAINT fk_payments_rider_id FOREIGN KEY (rider_id) REFERENCES riders(user_id) ON DELETE RESTRICT
);

-- Indexes
CREATE INDEX idx_payments_rider_id ON payments(rider_id);
CREATE INDEX idx_payments_ride_id ON payments(ride_id);
CREATE INDEX idx_payments_status ON payments(status);
CREATE INDEX idx_payments_payment_id ON payments(payment_id);
CREATE INDEX idx_payments_gateway_payment_id ON payments(gateway_payment_id);
