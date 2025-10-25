CREATE TABLE vehicles (
    id BIGSERIAL PRIMARY KEY,
    driver_id BIGINT NOT NULL UNIQUE,
    vehicle_type VARCHAR(20) NOT NULL CHECK (vehicle_type IN ('BIKE', 'SEDAN', 'SUV')),
    make VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    color VARCHAR(30),
    registration_number VARCHAR(20) NOT NULL UNIQUE,
    registration_year INTEGER NOT NULL,
    seating_capacity INTEGER NOT NULL,
    vehicle_image_url VARCHAR(500),
    insurance_number VARCHAR(50),
    insurance_expiry DATE,
    is_verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_vehicles_driver_id FOREIGN KEY (driver_id) REFERENCES drivers(user_id) ON DELETE CASCADE
);

-- Indexes
CREATE INDEX idx_vehicles_driver_id ON vehicles(driver_id);
CREATE INDEX idx_vehicles_registration_number ON vehicles(registration_number);