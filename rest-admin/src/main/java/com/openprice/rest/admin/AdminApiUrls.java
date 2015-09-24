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
    String URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE_BASE64 = "/admin/users/{userId}/receipts/{receiptId}/images/{imageId}/base64";
    String URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT_ITEMS = "/admin/users/{userId}/receipts/{receiptId}/items";

    String URL_ADMIN_RECEIPTS = "/admin/receipts";
    String URL_ADMIN_RECEIPTS_RECEIPT = "/admin/receipts/{receiptId}";
    String URL_ADMIN_RECEIPTS_RECEIPT_IMAGES = "/admin/receipts/{receiptId}/images";
    String URL_ADMIN_RECEIPTS_RECEIPT_IMAGES_IMAGE = "/admin/receipts/{receiptId}/images/{imageId}";
    String URL_ADMIN_RECEIPTS_RECEIPT_IMAGES_IMAGE_DOWNLOAD = "/admin/receipts/{receiptId}/images/{imageId}/download";
    String URL_ADMIN_RECEIPTS_RECEIPT_IMAGES_IMAGE_BASE64 = "/admin/receipts/{receiptId}/images/{imageId}/base64";
    String URL_ADMIN_RECEIPTS_RECEIPT_ITEMS = "/admin/receipts/{receiptId}/items";

    String URL_ADMIN_STORES = "/admin/stores";
    String URL_ADMIN_STORES_STORE = "/admin/stores/{storeId}";
    String URL_ADMIN_STORES_STORE_BRANCHES = "/admin/stores/{storeId}/branches";
    String URL_ADMIN_STORES_STORE_BRANCHES_BRANCH = "/admin/stores/{storeId}/branches/{branchId}";
}
