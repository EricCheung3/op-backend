package com.openprice.rest.user;

import static com.jayway.restassured.RestAssured.given;

import org.springframework.boot.test.SpringApplicationConfiguration;

import com.damnhandy.uri.template.UriTemplate;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.response.Response;
import com.openprice.rest.AbstractRestApiIntegrationTest;
import com.openprice.rest.UtilConstants;

@SpringApplicationConfiguration(classes = {UserApiTestApplication.class})
public abstract class AbstractUserRestApiIntegrationTest extends AbstractRestApiIntegrationTest {

    protected UserProfileForm constructUserProfileFormByProfileResource(final Response response) {
        final UserProfileForm form = new UserProfileForm();
        form.setFirstName(response.then().extract().path("firstName"));
        form.setMiddleName(response.then().extract().path("middleName"));
        form.setLastName(response.then().extract().path("lastName"));
        form.setPhone(response.then().extract().path("phone"));
        form.setAddress1(response.then().extract().path("address.address1"));
        form.setAddress2(response.then().extract().path("address.address2"));
        form.setCity(response.then().extract().path("address.city"));
        form.setState(response.then().extract().path("address.state"));
        form.setZip(response.then().extract().path("address.zip"));
        form.setCountry(response.then().extract().path("address.country"));
        return form;
    }

    protected String userUrl() {
        return UtilConstants.API_ROOT + UserApiUrls.URL_USER;
    }

    protected String userProfileUrl() {
        return UtilConstants.API_ROOT + UserApiUrls.URL_USER_PROFILE;
    }

    protected String userReceiptUploadUrl(final SessionFilter sessionFilter) {
        return given().filter(sessionFilter)
                      .when().get(UtilConstants.API_ROOT + UserApiUrls.URL_USER)
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

}
