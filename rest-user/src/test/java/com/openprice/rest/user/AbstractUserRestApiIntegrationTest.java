package com.openprice.rest.user;

import org.springframework.boot.test.SpringApplicationConfiguration;

import com.jayway.restassured.response.Response;
import com.openprice.rest.AbstractRestApiIntegrationTest;

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

}
