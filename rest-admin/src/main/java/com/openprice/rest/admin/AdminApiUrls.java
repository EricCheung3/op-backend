package com.openprice.rest.admin;

/**
 * Urls for admin REST API endpoints.
 *
 */
public interface AdminApiUrls {

    String URL_ADMIN = "/admin";
    String URL_ADMIN_USERS = "/admin/users";
    String URL_ADMIN_USERS_USER = "/admin/users/{userId}";
    String URL_ADMIN_USERS_USER_LOCK_STATE = "/admin/users/{userId}/lockState";
    String URL_ADMIN_USERS_USER_PROFILE = "/admin/users/{userId}/profile";

    String URL_ADMIN_USERS_USER_RECEIPTS = "/admin/users/{userId}/receipts";
    String URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT = "/admin/users/{userId}/receipts/{receiptId}";
    String URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT_IMAGES = "/admin/users/{userId}/receipts/{receiptId}/images";
    String URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE = "/admin/users/{userId}/receipts/{receiptId}/images/{imageId}";
    String URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE_DOWNLOAD = "/admin/users/{userId}/receipts/{receiptId}/images/{imageId}/download";

}
