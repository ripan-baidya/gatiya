package com.gatiya.notification.enums;

/**
 * Representing the possible notification types.
 */
public enum NotificationType {
    RIDE_REQUEST,
    RIDE_ACCEPTED,
    RIDE_CANCELLED,
    DRIVER_ARRIVED,
    RIDE_STARTED,
    RIDE_COMPLETED,
    PAYMENT_SUCCESS,
    PAYMENT_FAILED

    // ***** More notification will be added in the future *****
    /*
    RIDE_OFFER_RECEIVED,
    RATING_RECEIVED,
    ACCOUNT_VERIFIED,
    DOCUMENT_APPROVED,
    DOCUMENT_REJECTED,
    PROMO_CODE_APPLIED,
    WALLET_CREDITED,
    WALLET_DEBITED
    */
}