package com.openprice.rest.user;

import static com.jayway.restassured.RestAssured.given;

import org.springframework.boot.test.SpringApplicationConfiguration;

import com.damnhandy.uri.template.UriTemplate;
import com.jayway.restassured.filter.session.SessionFilter;
import com.openprice.rest.AbstractRestApiIntegrationTest;
import com.openprice.rest.UtilConstants;

@SpringApplicationConfiguration(classes = {UserApiTestApplication.class})
public abstract class AbstractUserRestApiIntegrationTest extends AbstractRestApiIntegrationTest {

    protected String userUrl() {
        return UtilConstants.API_ROOT + UserApiUrls.URL_USER;
    }

    protected String userProfileUrl() {
        return UtilConstants.API_ROOT + UserApiUrls.URL_USER_PROFILE;
    }

    protected String userReceiptUploadUrl(final SessionFilter sessionFilter) {
        return given().filter(sessionFilter)
                      .when().get(userUrl())
                      .then().extract().path("_links.upload.href");
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

    protected String userReceiptParserResultUrl(final SessionFilter sessionFilter, final String receiptId) {
        return
            given().filter(sessionFilter)
                   .when().get(userReceiptUrl(sessionFilter, receiptId))
                   .then().extract().path("_links.result.href");
    }

    protected String userReceiptParserResultItemsUrl(final SessionFilter sessionFilter, final String receiptId) {
        final String itemsLink =
            given().filter(sessionFilter)
                   .when().get(userReceiptUrl(sessionFilter, receiptId))
                   .then().extract().path("_links.items.href");
        return UriTemplate.fromTemplate(itemsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String userReceiptParserResultItemUrl(final SessionFilter sessionFilter, final String receiptId, final String itemId) {
        final String itemLink =
            given().filter(sessionFilter)
                   .when().get(userReceiptUrl(sessionFilter, receiptId))
                   .then().extract().path("_links.item.href");
        return UriTemplate.fromTemplate(itemLink).set("itemId", itemId).expand();
    }

    protected String userStoresUrl(final SessionFilter sessionFilter) {
        final String storesLink =
            given().filter(sessionFilter)
                   .when().get(userUrl())
                   .then().extract().path("_links.stores.href");
        return UriTemplate.fromTemplate(storesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String userStoreUrl(final SessionFilter sessionFilter, final String storeId) {
        final String storeLink =
            given().filter(sessionFilter)
                   .when().get(userUrl())
                   .then().extract().path("_links.store.href");
        return UriTemplate.fromTemplate(storeLink).set("storeId", storeId).expand();
    }

    protected String userStoreItemsUrl(final SessionFilter sessionFilter, final String storeId) {
        final String itemsLink =
            given().filter(sessionFilter)
                   .when().get(userStoreUrl(sessionFilter, storeId))
                   .then().extract().path("_links.items.href");
        return UriTemplate.fromTemplate(itemsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String userStoreItemUrl(final SessionFilter sessionFilter, final String storeId, final String itemId) {
        final String storeLink =
            given().filter(sessionFilter)
                   .when().get(userStoreUrl(sessionFilter, storeId))
                   .then().extract().path("_links.item.href");
        return UriTemplate.fromTemplate(storeLink).set("itemId", itemId).expand();
    }

    protected String userShoppingListUploadUrl(final SessionFilter sessionFilter) {
        return given().filter(sessionFilter)
                      .when().get(userUrl())
                      .then().extract().path("_links.shoppingList.href");
    }

}
