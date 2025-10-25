CREATE TABLE saved_addresses (
    id BIGSERIAL PRIMARY KEY,
    rider_id BIGINT NOT NULL,
    label VARCHAR(50) NOT NULL,
    latitude DECIMAL(10, 7) NOT NULL,
    longitude DECIMAL(10, 7) NOT NULL,
    address_line VARCHAR(500) NOT NULL,
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(50),
    pin_code VARCHAR(20),
    landmark VARCHAR(200),
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_saved_addresses_rider_id FOREIGN KEY (rider_id) REFERENCES riders(user_id) ON DELETE CASCADE
);

-- Indexes
CREATE INDEX idx_saved_addresses_rider_id ON saved_addresses(rider_id);