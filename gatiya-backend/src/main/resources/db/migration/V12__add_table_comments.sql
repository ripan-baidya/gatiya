-- Users table comments
COMMENT ON TABLE users IS 'Core users table for all user types (Rider, Driver, Admin)';
COMMENT ON COLUMN users.firebase_uid IS 'Firebase Authentication UID for the user';
COMMENT ON COLUMN users.role IS 'User role: RIDER, DRIVER, or ADMIN';
COMMENT ON COLUMN users.status IS 'Account status: ACTIVE, INACTIVE, SUSPENDED, PENDING_VERIFICATION, BANNED';

-- Riders table comments
COMMENT ON TABLE riders IS 'Extended profile for riders (inherits from users)';
COMMENT ON COLUMN riders.wallet_balance IS 'Current wallet balance in INR';

-- Drivers table comments
COMMENT ON TABLE drivers IS 'Extended profile for drivers (inherits from users)';
COMMENT ON COLUMN drivers.verification_status IS 'Driver document verification status';
COMMENT ON COLUMN drivers.availability_status IS 'Real-time availability: AVAILABLE, UNAVAILABLE, OCCUPIED, BUSY';

-- Rides table comments
COMMENT ON TABLE rides IS 'Core rides table tracking all ride requests and completions';
COMMENT ON COLUMN rides.ride_otp IS '6-digit OTP for ride verification at pickup';
COMMENT ON COLUMN rides.pricing_mode IS 'Currently only DISTANCE_BASED is supported';

-- Payments table comments
COMMENT ON TABLE payments IS 'Payment transactions for completed rides';
COMMENT ON COLUMN payments.payment_id IS 'Internal payment tracking ID';
COMMENT ON COLUMN payments.gateway_payment_id IS 'Razorpay payment ID';

-- Ratings table comments
COMMENT ON TABLE ratings IS 'Ride ratings from riders to drivers';
COMMENT ON COLUMN ratings.rider_to_driver_rating IS 'Rating from 1-5 stars';

-- Notifications table comments
COMMENT ON TABLE notifications IS 'Push notifications and in-app messages for users';
COMMENT ON COLUMN notifications.type IS 'Notification type for categorization and handling';