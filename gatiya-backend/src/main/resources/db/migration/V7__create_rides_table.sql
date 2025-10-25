CREATE TABLE rides (
    id BIGSERIAL PRIMARY KEY,
    ride_code VARCHAR(50) NOT NULL UNIQUE,
    rider_id BIGINT NOT NULL,
    driver_id BIGINT,
    status VARCHAR(50) NOT NULL CHECK (status IN ('REQUESTED', 'SEARCHING_FOR_DRIVER', 'DRIVER_ASSIGNED', 'DRIVER_EN_ROUTE_TO_PICKUP', 'DRIVER_ARRIVED_AT_PICKUP', 'STARTED', 'ONGOING', 'COMPLETED', 'CANCELLED')),
    requested_vehicle_type VARCHAR(30) NOT NULL CHECK (requested_vehicle_type IN ('BIKE', 'SEDAN', 'SUV')),
    pricing_mode VARCHAR(20) NOT NULL DEFAULT 'DISTANCE_BASED' CHECK (pricing_mode IN ('DISTANCE_BASED')),

    -- Pickup Location
    pickup_latitude DECIMAL(10, 7) NOT NULL,
    pickup_longitude DECIMAL(10, 7) NOT NULL,
    pickup_address VARCHAR(500) NOT NULL,

    -- Drop Location
    drop_latitude DECIMAL(10, 7) NOT NULL,
    drop_longitude DECIMAL(10, 7) NOT NULL,
    drop_address VARCHAR(500) NOT NULL,

    -- Ride Metrics
    estimated_distance DECIMAL(10, 2),
    estimated_duration INTEGER,
    estimated_fare DECIMAL(10, 2),
    actual_distance DECIMAL(10, 2),
    actual_duration INTEGER,
    final_fare DECIMAL(10, 2),
    platform_commission DECIMAL(10, 2),
    driver_earnings DECIMAL(10, 2),

    -- OTP
    ride_otp VARCHAR(6),

    -- Timestamps
    requested_at TIMESTAMP NOT NULL,
    confirmed_at TIMESTAMP,
    started_at TIMESTAMP,
    completed_at TIMESTAMP,
    cancelled_at TIMESTAMP,

    -- Cancellation Info
    cancelled_by VARCHAR(20) CHECK (cancelled_by IN ('RIDER', 'DRIVER', 'SYSTEM', 'ADMIN')),
    cancellation_reason VARCHAR(500),

    -- Route
    route_polyline VARCHAR(5000),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,

    CONSTRAINT fk_rides_rider_id FOREIGN KEY (rider_id) REFERENCES riders(user_id) ON DELETE RESTRICT,
    CONSTRAINT fk_rides_driver_id FOREIGN KEY (driver_id) REFERENCES drivers(user_id) ON DELETE RESTRICT
);

-- Indexes
CREATE INDEX idx_rides_rider_id ON rides(rider_id);
CREATE INDEX idx_rides_driver_id ON rides(driver_id);
CREATE INDEX idx_rides_status ON rides(status);
CREATE INDEX idx_rides_requested_at ON rides(requested_at);
CREATE INDEX idx_rides_ride_code ON rides(ride_code);