package com.openprice.rest.site;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.endsWith;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.jayway.restassured.http.ContentType;
import com.openprice.common.ApiConstants;
import com.openprice.rest.UtilConstants;

public class WebsiteRestApiIT extends AbstractSiteRestApiIntegrationTest implements SiteApiUrls {

    @Test
    public void getPublic_ShouldReturnWebsiteResource() {
        when()
            .get(siteUrl())
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("_links.self.href", endsWith(ApiConstants.EXTERNAL_API_ROOT))
            .body("_links.signin.href", endsWith(UtilConstants.URL_SIGNIN))
            .body("_links.registration.href", endsWith(URL_PUBLIC_REGISTRATION))
            .body("_links.forgetPassword.href", endsWith(URL_PUBLIC_RESET_PASSWORD_REQUESTS))
            .body("_links.resetPassword.href", endsWith(URL_PUBLIC_RESET_PASSWORD_REQUESTS_REQUEST))
        ;
    }

    @Test
    public void sendContactMessage_ShouldReturnOK() {
        final ContactForm form = new ContactForm("John Doe", "john.doe@email.com", "Hello!");

        given()
            .contentType(ContentType.JSON)
            .body(form)
        .when()
            .post(contactUrl())
        .then()
            .statusCode(HttpStatus.SC_OK)
        ;

    }
}
