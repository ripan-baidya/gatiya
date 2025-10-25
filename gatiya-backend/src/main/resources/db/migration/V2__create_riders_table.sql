CREATE TABLE riders (
    user_id BIGINT PRIMARY KEY,
    wallet_balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    total_rides INTEGER NOT NULL DEFAULT 0,
    average_rating DECIMAL(3, 2) NOT NULL DEFAULT 0.00,
    total_ratings INTEGER NOT NULL DEFAULT 0,
    CONSTRAINT fk_riders_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);