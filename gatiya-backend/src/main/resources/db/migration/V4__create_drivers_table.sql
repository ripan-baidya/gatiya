CREATE TABLE drivers (
    user_id BIGINT PRIMARY KEY,
    license_number VARCHAR(50) NOT NULL UNIQUE,
    license_expiry_date DATE NOT NULL,
    verification_status VARCHAR(30) NOT NULL CHECK (verification_status IN ('PENDING', 'DOCUMENTS_UPLOADED', 'VERIFIED', 'REJECTED')),
    availability_status VARCHAR(20) NOT NULL CHECK (availability_status IN ('AVAILABLE', 'UNAVAILABLE', 'OCCUPIED', 'BUSY')),
    last_known_latitude DECIMAL(10, 7),
    last_known_longitude DECIMAL(10, 7),
    last_location_updated_at TIMESTAMP,
    wallet_balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    total_rides INTEGER NOT NULL DEFAULT 0,
    average_rating DECIMAL(3, 2) NOT NULL DEFAULT 0.00,
    total_ratings INTEGER NOT NULL DEFAULT 0,
    total_earnings DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    fcm_token VARCHAR(500),
    CONSTRAINT fk_drivers_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Indexes
CREATE INDEX idx_drivers_availability_status ON drivers(availability_status);
CREATE INDEX idx_drivers_verification_status ON drivers(verification_status);