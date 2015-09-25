package com.openprice.rest.user.store;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.damnhandy.uri.template.UriTemplate;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.user.AbstractUserRestApiIntegrationTest;
import com.openprice.rest.user.UserApiUrls;

@DatabaseSetup("classpath:/data/testData.xml")
public class UserStoreRestApiIT extends AbstractUserRestApiIntegrationTest {

    @Test
    public void getCurrentUserStores_ShouldReturnAllStores() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        // get stores link
        String storesLink =
                given().filter(sessionFilter)
                       .when().get(UtilConstants.API_ROOT + UserApiUrls.URL_USER)
                       .then().extract().path("_links.stores.href");
        String receiptsUrl =  UriTemplate.fromTemplate(storesLink)
                                         .set("page", 0)
                                         .set("size", 10)
                                         .set("sort", null)
                                         .expand();

        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(receiptsUrl)
            ;
        //response.prettyPrint();

        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(10))
            .body("page.totalElements", equalTo(3))
            .body("page.totalPages", equalTo(1))
            .body("page.number", equalTo(0))
            .body("_embedded.storeChains[0].id", equalTo("store001"))
            .body("_embedded.storeChains[1].id", equalTo("store002"))
            .body("_embedded.storeChains[2].id", equalTo("store003"))
        ;
    }

    @Test
    public void getUserStoreById_ShouldReturnStore() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        final String storeLink =
                given().filter(sessionFilter)
                       .when().get(UtilConstants.API_ROOT + UserApiUrls.URL_USER)
                       .then().extract().path("_links.store.href");
        final String storeUrl =
                UriTemplate.fromTemplate(storeLink)
                           .set("storeId", "store001")
                           .expand();

        final Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(storeUrl)
            ;

        //response.prettyPrint();

        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("store001"))
            .body("name", equalTo("Real Canadian Superstore"))
            .body("_links.self.href", endsWith(storeUrl))
            .body("_links.user.href", endsWith("/user"))
            .body("_links.items.href", endsWith(storeUrl + "/items"))
            .body("_links.item.href", endsWith(storeUrl + "/items/{itemId}"))
        ;
    }

}
