package com.openprice.rest.user;

import static com.jayway.restassured.RestAssured.given;

import com.damnhandy.uri.template.UriTemplate;
import com.jayway.restassured.filter.session.SessionFilter;
import com.openprice.common.ApiConstants;
import com.openprice.rest.AbstractRestApiIntegrationTest;

public abstract class AbstractUserRestApiIntegrationTest extends AbstractRestApiIntegrationTest implements UserApiUrls {

    protected String userUrl() {
        return ApiConstants.EXTERNAL_API_ROOT + URL_USER;
    }

    protected String userProfileUrl() {
        return ApiConstants.EXTERNAL_API_ROOT + URL_USER_PROFILE;
    }

    protected String userReceiptUploadUrl(final SessionFilter sessionFilter) {
        return given().filter(sessionFilter)
                      .when().get(userUrl())
                      .then().extract().path("_links.upload.href");
    }

    protected String userReceiptHackloadUrl(final SessionFilter sessionFilter) {
        return given().filter(sessionFilter)
                      .when().get(userUrl())
                      .then().extract().path("_links.hackload.href");
    }

    protected String userReceiptsUrl(final SessionFilter sessionFilter) {
        final String receiptsLink =
            given().filter(sessionFilter)
                   .when().get(userUrl())
                   .then().extract().path("_links.receipts.href");
        return UriTemplate.fromTemplate(receiptsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String userReceiptUrl(final SessionFilter sessionFilter, final String receiptId) {
        final String receiptLink =
            given().filter(sessionFilter)
                   .when().get(userUrl())
                   .then().extract().path("_links.receipt.href");
        return UriTemplate.fromTemplate(receiptLink).set("receiptId", receiptId).expand();
    }

    protected String userReceiptImagesUrl(final SessionFilter sessionFilter, final String receiptId) {
        final String imagesLink =
            given().filter(sessionFilter)
                   .when().get(userReceiptUrl(sessionFilter, receiptId))
                   .then().extract().path("_links.images.href");
        return UriTemplate.fromTemplate(imagesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String userReceiptImageUrl(final SessionFilter sessionFilter, final String receiptId, final String imageId) {
        final String imageLink =
            given().filter(sessionFilter)
                   .when().get(userReceiptUrl(sessionFilter, receiptId))
                   .then().extract().path("_links.image.href");
        return UriTemplate.fromTemplate(imageLink).set("imageId", imageId).expand();
    }

    protected String userReceiptDatatUrl(final SessionFilter sessionFilter, final String receiptId) {
        return
            given().filter(sessionFilter)
                   .when().get(userReceiptUrl(sessionFilter, receiptId))
                   .then().extract().path("_links.result.href");
    }

    protected String userReceiptItemsUrl(final SessionFilter sessionFilter, final String receiptId) {
        final String itemsLink =
            given().filter(sessionFilter)
                   .when().get(userReceiptUrl(sessionFilter, receiptId))
                   .then().extract().path("_links.items.href");
        return UriTemplate.fromTemplate(itemsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String userReceiptItemUrl(final SessionFilter sessionFilter, final String receiptId, final String itemId) {
        final String itemLink =
            given().filter(sessionFilter)
                   .when().get(userReceiptUrl(sessionFilter, receiptId))
                   .then().extract().path("_links.item.href");
        return UriTemplate.fromTemplate(itemLink).set("itemId", itemId).expand();
    }

    protected String userReceiptFeedbackUrl(final SessionFilter sessionFilter, final String receiptId) {
        return
            given().filter(sessionFilter)
                   .when().get(userReceiptUrl(sessionFilter, receiptId))
                   .then().extract().path("_links.feedback.href");
    }

    protected String userShoppingStoresUrl(final SessionFilter sessionFilter) {
        final String storesLink =
            given().filter(sessionFilter)
                   .when().get(userUrl())
                   .then().extract().path("_links.stores.href");
        return UriTemplate.fromTemplate(storesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String userShoppingStoreUrl(final SessionFilter sessionFilter, final String storeId) {
        final String storeLink =
            given().filter(sessionFilter)
                   .when().get(userUrl())
                   .then().extract().path("_links.store.href");
        return UriTemplate.fromTemplate(storeLink).set("storeId", storeId).expand();
    }

    protected String userShoppingItemsUrl(final SessionFilter sessionFilter, final String storeId) {
        final String itemsLink =
            given().filter(sessionFilter)
                   .when().get(userShoppingStoreUrl(sessionFilter, storeId))
                   .then().extract().path("_links.items.href");
        return UriTemplate.fromTemplate(itemsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String userShoppingItemUrl(final SessionFilter sessionFilter, final String storeId, final String itemId) {
        final String storeLink =
            given().filter(sessionFilter)
                   .when().get(userShoppingStoreUrl(sessionFilter, storeId))
                   .then().extract().path("_links.item.href");
        return UriTemplate.fromTemplate(storeLink).set("itemId", itemId).expand();
    }

    protected String userShoppingListUploadUrl(final SessionFilter sessionFilter) {
        return given().filter(sessionFilter)
                      .when().get(userUrl())
                      .then().extract().path("_links.shoppingList.href");
    }

}
