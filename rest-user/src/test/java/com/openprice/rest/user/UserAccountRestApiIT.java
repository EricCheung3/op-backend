package com.openprice.rest.user;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.domain.account.UserRoleType;
import com.openprice.rest.AbstractRestApiIntegrationTest;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.common.UserProfileForm;

@DatabaseSetup("classpath:/data/testData.xml")
public class UserAccountRestApiIT extends AbstractRestApiIntegrationTest {
    @Test
    public void getCurrentUser_ShouldReturnLoggedInUserAccount() {
        final SessionFilter sessionFilter = login("john.doe");
        
        Response response = 
            given()
                .filter(sessionFilter)
            .when()
                .get(UtilConstants.API_ROOT + UserApiUrls.URL_USER)
            ;

        //response.prettyPrint();

        response
            .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("id", equalTo("user001"))
                .body("email", equalTo("user1@openprice.com"))
                .body("roles[0]", equalTo(UserRoleType.ROLE_USER.name()))
                .body("roles[1]", equalTo(UserRoleType.ROLE_SUPER_ADMIN.name()))
                .body("accountNonLocked", equalTo(true))
                .body("accountNonExpired", equalTo(true))
                .body("enabled", equalTo(true))
                .body("profile.id", equalTo("profile001"))
                .body("profile.firstName", equalTo("John"))
                .body("profile.lastName", equalTo("Doe"))
                .body("_links.self.href", endsWith(UserApiUrls.URL_USER))
                .body("_links.profile.href", endsWith(UserApiUrls.URL_USER_PROFILE))
                .body("_links.receipts.href", endsWith(UserApiUrls.URL_USER_RECEIPTS))
                .body("_links.receipt.href", endsWith(UserApiUrls.URL_USER_RECEIPTS_RECEIPT))
                .body("_links.upload.href", endsWith(UserApiUrls.URL_USER_RECEIPTS_UPLOAD))
            ;
    }

    @Test
    public void getCurrentUserProfile_ShouldReturnLoggedInUserProfile() {
        final SessionFilter sessionFilter = login("jane.doe");

        Response response = 
            given()
                .filter(sessionFilter)
            .when()
                .get(UtilConstants.API_ROOT + UserApiUrls.URL_USER_PROFILE)
            ;

        //response.prettyPrint();

        response
            .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("id", equalTo("profile002"))
                .body("firstName", equalTo("Jane"))
                .body("lastName", equalTo("Doe"))
            ;

    }
    
    @Test
    public void updateProfile_ShouldChangeProfileAddress() {
        final SessionFilter sessionFilter = login("jane.doe");
        
        Response response = 
            given()
                .filter(sessionFilter)
            .when()
                .get(UtilConstants.API_ROOT + UserApiUrls.URL_USER_PROFILE)
            ;
        
        //response.prettyPrint();

        response
            .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("id", equalTo("profile002"))
                .body("address.address1", equalTo("101 123 street"))
                .body("address.city", equalTo("Edmonton"))
            ;

        UserProfileForm form = constructUserProfileFormByProfileResource(response);
        form.setAddress1("888 Broadway Ave");
        form.setCity("Calgary");

        given()
            .filter(sessionFilter)
            .contentType(ContentType.JSON)
            .body(form)
        .when()
            .put(UtilConstants.API_ROOT + UserApiUrls.URL_USER_PROFILE)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        response = 
            given()
                .filter(sessionFilter)
            .when()
                .get(UtilConstants.API_ROOT + UserApiUrls.URL_USER_PROFILE)
            ;

        //response.prettyPrint();

        response
            .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("id", equalTo("profile002"))
                .body("address.address1", equalTo("888 Broadway Ave"))
                .body("address.city", equalTo("Calgary"))
            ;
    }

}
