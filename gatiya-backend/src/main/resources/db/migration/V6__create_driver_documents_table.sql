CREATE TABLE driver_documents (
    id BIGSERIAL PRIMARY KEY,
    driver_id BIGINT NOT NULL,
    document_type VARCHAR(30) NOT NULL CHECK (document_type IN ('DRIVING_LICENSE', 'AADHAR_CARD', 'PAN_CARD', 'VEHICLE_RC', 'VEHICLE_INSURANCE', 'PROFILE_PHOTO')),
    document_url VARCHAR(500) NOT NULL,
    document_number VARCHAR(50) UNIQUE,
    verification_status VARCHAR(20) NOT NULL CHECK (verification_status IN ('PENDING', 'APPROVED', 'REJECTED')),
    rejection_reason VARCHAR(500),
    expiry_date DATE,
    uploaded_at TIMESTAMP NOT NULL,
    verified_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_driver_documents_driver_id FOREIGN KEY (driver_id) REFERENCES drivers(user_id) ON DELETE CASCADE
);

-- Indexes
CREATE INDEX idx_driver_documents_driver_id ON driver_documents(driver_id);
CREATE INDEX idx_driver_documents_document_type ON driver_documents(document_type);
CREATE INDEX idx_driver_documents_verification_status ON driver_documents(verification_status);
CREATE INDEX idx_driver_documents_expiry_date ON driver_documents(expiry_date);