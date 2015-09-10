package com.openprice.rest.admin;

import static com.jayway.restassured.RestAssured.given;

import org.springframework.boot.test.SpringApplicationConfiguration;

import com.damnhandy.uri.template.UriTemplate;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.response.Response;
import com.openprice.rest.AbstractRestApiIntegrationTest;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.admin.user.UserProfileForm;

@SpringApplicationConfiguration(classes = {AdminApiTestApplication.class})
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

}
