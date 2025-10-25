CREATE TABLE ratings (
    id BIGSERIAL PRIMARY KEY,
    ride_id BIGINT NOT NULL UNIQUE,
    rider_to_driver_rating INTEGER CHECK (rider_to_driver_rating BETWEEN 1 AND 5),
    rider_to_driver_comment VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_ratings_ride_id FOREIGN KEY (ride_id) REFERENCES rides(id) ON DELETE CASCADE
);

-- Indexes
CREATE INDEX idx_ratings_ride_id ON ratings(ride_id);