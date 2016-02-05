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
    String URL_ADMIN_RECEIPTS_RECEIPT_RESULTS = "/admin/receipts/{receiptId}/results";
    String URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT = "/admin/receipts/{receiptId}/results/{resultId}";
    String URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT_ITEMS = "/admin/receipts/{receiptId}/results/{resultId}/items";
    String URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT_ITEMS_ITEM = "/admin/receipts/{receiptId}/results/{resultId}/items/{itemId}";

    String URL_ADMIN_RECEIPTS_RECEIPT_FEEDBACKS = "/admin/receipts/{receiptId}/feedbacks";
    String URL_ADMIN_RECEIPTS_RECEIPT_FEEDBACKS_FEEDBACK = "/admin/receipts/{receiptId}/feedbacks/{feedbackId}";
    
    String URL_ADMIN_CHAINS = "/admin/chains";
    String URL_ADMIN_CHAINS_CHAIN = "/admin/chains/{chainCode}";
    String URL_ADMIN_CHAINS_CHAIN_BRANCHES = "/admin/chains/{chainCode}/branches";
    String URL_ADMIN_CHAINS_CHAIN_CATALOGS = "/admin/chains/{chainCode}/catalogs";
    String URL_ADMIN_CHAINS_CHAIN_CATALOGS_CATALOG = "/admin/chains/{chainCode}/catalogs/{catalogCode}";
}
