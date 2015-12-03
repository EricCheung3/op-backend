package com.openprice.rest.site;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.endsWith;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;

import com.jayway.restassured.http.ContentType;
import com.openprice.common.ApiConstants;
import com.openprice.rest.AbstractRestApiIntegrationTest;
import com.openprice.rest.UtilConstants;

@SpringApplicationConfiguration(classes = {SiteApiTestApplication.class})
public class WebsiteRestApiIT extends AbstractRestApiIntegrationTest implements SiteApiUrls {

    @Test
    public void getPublic_ShouldReturnWebsiteResource() {
        when()
            .get(ApiConstants.EXTERNAL_API_ROOT)
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

}
