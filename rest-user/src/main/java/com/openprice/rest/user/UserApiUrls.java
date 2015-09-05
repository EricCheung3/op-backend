package com.openprice.rest.user;

/**
 * Urls for user REST API endpoints.
 * 
 */
public interface UserApiUrls {
    
    String URL_USER = "/user";
    String URL_USER_PROFILE = "/user/profile";
    
    String URL_USER_RECEIPTS = "/user/receipts";
    String URL_USER_RECEIPTS_RECEIPT = "/user/receipts/{receiptId}";
    String URL_USER_RECEIPTS_RECEIPT_IMAGES = "/user/receipts/{receiptId}/images";
    String URL_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE = "/user/receipts/{receiptId}/images/{imageId}";
    String URL_USER_RECEIPTS_RECEIPT_ITEMS = "/user/receipts/{receiptId}/items";
    
    String URL_USER_RECEIPTS_UPLOAD = "/user/receipts/upload";
    String URL_USER_RECEIPTS_RECEIPT_IMAGES_UPLOAD = "/user/receipts/{receiptId}/images/upload";
    String URL_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE_DOWNLOAD = "/user/receipts/{receiptId}/images/{imageId}/download";
    String URL_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE_BASE64 = "/user/receipts/{receiptId}/images/{imageId}/base64";
    
    String URL_USER_SHOPPINGLIST = "/user/shoppingList";
    String URL_USER_SHOPPING_STORES = "/user/stores";
    String URL_USER_SHOPPING_STORES_STORE = "/user/stores/{storeId}";
    String URL_USER_SHOPPING_STORES_STORE_ITEMS = "/user/stores/{storeId}/items";
    String URL_USER_SHOPPING_STORES_STORE_ITEMS_ITEM = "/user/stores/{storeId}/items/{itemId}";
    
}
