package com.openprice.rest.site;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.damnhandy.uri.template.UriTemplate;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.rest.AbstractRestApiIntegrationTest;
import com.openprice.rest.UtilConstants;

public class RegisterRestApiIT extends AbstractRestApiIntegrationTest {
    
    @Test
    public void registerNewUser_ShouldAddUserAccount() {
        String registrationLink = when().get(UtilConstants.API_ROOT).then().extract().path("_links.registration.href");
        String registrationUrl =  UriTemplate.fromTemplate(registrationLink).set("username", "testuser").expand();
        
        RegistrationForm registration = new RegistrationForm();
        registration.setFirstName("John");
        registration.setLastName("Doe");
        registration.setUsername("testuser");
        registration.setPassword("abcd");
        registration.setEmail("john.doe@openprice.com");
        
        Response response = 
            given()
                .contentType(ContentType.JSON)
                .body(registration)
            .when()
                .post(registrationUrl)
            ;
        
        response
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            ;

    }

}
