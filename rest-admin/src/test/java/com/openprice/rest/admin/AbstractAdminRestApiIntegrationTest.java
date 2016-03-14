package com.openprice.rest.admin;

import static com.jayway.restassured.RestAssured.given;

import javax.inject.Inject;

import org.springframework.boot.test.SpringApplicationConfiguration;

import com.damnhandy.uri.template.UriTemplate;
import com.jayway.restassured.filter.session.SessionFilter;
import com.openprice.common.ApiConstants;
import com.openprice.domain.account.user.UserAccountRepository;
import com.openprice.rest.AbstractRestApiIntegrationTest;

@SpringApplicationConfiguration(classes = {AdminApiTestApplication.class})
public abstract class AbstractAdminRestApiIntegrationTest extends AbstractRestApiIntegrationTest implements AdminApiUrls {

    @Inject
    UserAccountRepository userAccountRepository;

    public static final String TEST_ADMIN_USERID_NEWTON = "admin001";
    public static final String TEST_ADMIN_USERNAME_NEWTON = "newton";
    public static final String TEST_ADMIN_USERID_EINSTEIN = "admin002";
    public static final String TEST_ADMIN_USERNAME_EINSTEIN = "einstein";
    public static final String TEST_ADMIN_USERID_HAWKING = "admin003";
    public static final String TEST_ADMIN_USERNAME_HAWKING = "hawking";

    protected String adminUrl() {
        return ApiConstants.EXTERNAL_API_ROOT + URL_ADMIN;
    }

    protected String usersUrl(final SessionFilter sessionFilter) {
        final String usersLink =
            given().filter(sessionFilter)
                   .when().get(adminUrl())
                   .then().extract().path("_links.users.href");
        return UriTemplate.fromTemplate(usersLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String userUrl(final SessionFilter sessionFilter, final String userId) {
        final String userLink =
            given().filter(sessionFilter)
                   .when().get(adminUrl())
                   .then().extract().path("_links.user.href");
        return UriTemplate.fromTemplate(userLink).set("userId", userId).expand();
    }
}
