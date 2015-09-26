package com.openprice.rest.admin;

import static com.jayway.restassured.RestAssured.given;

import org.springframework.boot.test.SpringApplicationConfiguration;

import com.damnhandy.uri.template.UriTemplate;
import com.jayway.restassured.filter.session.SessionFilter;
import com.openprice.rest.AbstractRestApiIntegrationTest;
import com.openprice.rest.UtilConstants;

@SpringApplicationConfiguration(classes = {AdminApiTestApplication.class})
public abstract class AbstractAdminRestApiIntegrationTest extends AbstractRestApiIntegrationTest {

    public static final String TEST_ADMIN_USERID_JOHN_DOE = "admin001";
    public static final String TEST_ADMIN_USERNAME_JOHN_DOE = "john.doe";
    public static final String TEST_ADMIN_USERID_JANE_DOE = "admin002";
    public static final String TEST_ADMIN_USERNAME_JANE_DOE = "jane.doe";
    public static final String TEST_ADMIN_USERID_JUNIOR_DOE = "admin003";
    public static final String TEST_ADMIN_USERNAME_JUNIOR_DOE = "junior.doe";

    protected String usersUrl(final SessionFilter sessionFilter) {
        final String usersLink =
            given().filter(sessionFilter)
                   .when().get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN)
                   .then().extract().path("_links.users.href");
        return UriTemplate.fromTemplate(usersLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String userUrl(final SessionFilter sessionFilter, final String userId) {
        final String chainLink =
            given().filter(sessionFilter)
                   .when().get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN)
                   .then().extract().path("_links.user.href");
        return UriTemplate.fromTemplate(chainLink).set("userId", userId).expand();
    }
}
