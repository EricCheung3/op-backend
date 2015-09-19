package com.openprice.rest.site;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.endsWith;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;

import com.jayway.restassured.http.ContentType;
import com.openprice.rest.AbstractRestApiIntegrationTest;
import com.openprice.rest.UtilConstants;

@SpringApplicationConfiguration(classes = {SiteApiTestApplication.class})
public class WebsiteRestApiIT extends AbstractRestApiIntegrationTest {

    @Test
    public void getPublic_ShouldReturnWebsiteResource() {
        when()
            .get(UtilConstants.API_ROOT)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("_links.self.href", endsWith(UtilConstants.API_ROOT))
            .body("_links.signin.href", endsWith(UtilConstants.URL_SIGNIN))
            .body("_links.registration.href", endsWith(SiteApiUrls.URL_REGISTRATION_USERS))
        ;
    }

}
