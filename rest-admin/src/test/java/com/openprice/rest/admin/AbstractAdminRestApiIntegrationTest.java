package com.openprice.rest.admin;

import static com.jayway.restassured.RestAssured.given;

import com.damnhandy.uri.template.UriTemplate;
import com.jayway.restassured.filter.session.SessionFilter;
import com.openprice.rest.AbstractRestApiIntegrationTest;
import com.openprice.rest.UtilConstants;

public abstract class AbstractAdminRestApiIntegrationTest extends AbstractRestApiIntegrationTest {
    
    protected String getAdminAccountUsersLink(final SessionFilter sessionFilter) {
        return given().filter(sessionFilter)
                      .when().get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN)
                      .then().extract().path("_links.users.href");
    }

    protected String getUserLinkUrl(final SessionFilter sessionFilter, final String userId) {
        String userLink = given().filter(sessionFilter)
                                 .when().get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN)
                                 .then().extract().path("_links.user.href");
        return UriTemplate.fromTemplate(userLink).set("userId", userId).expand();

    }

}
