package com.openprice.rest.site;

import static com.jayway.restassured.RestAssured.when;

import org.springframework.boot.test.SpringApplicationConfiguration;

import com.openprice.common.ApiConstants;
import com.openprice.rest.AbstractRestApiIntegrationTest;

@SpringApplicationConfiguration(classes = {SiteApiTestApplication.class})
public abstract class AbstractSiteRestApiIntegrationTest extends AbstractRestApiIntegrationTest implements SiteApiUrls {

    protected String siteUrl() {
        return ApiConstants.EXTERNAL_API_ROOT;
    }

    protected String registrationUrl() {
        return
            when().get(ApiConstants.EXTERNAL_API_ROOT)
                  .then().extract().path("_links.registration.href");
    }

    protected String forgetPasswordUrl() {
        return  when().get(ApiConstants.EXTERNAL_API_ROOT).then().extract().path("_links.forgetPassword.href");
    }

    protected String resetPasswordRequestUrl() {
        return  when().get(ApiConstants.EXTERNAL_API_ROOT).then().extract().path("_links.resetPassword.href");
    }

}
