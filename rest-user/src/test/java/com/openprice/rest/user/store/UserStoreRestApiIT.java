package com.openprice.rest.user.store;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.junit.Ignore;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.user.AbstractUserRestApiIntegrationTest;

@DatabaseSetup("classpath:/data/testData.xml")
@Ignore
public class UserStoreRestApiIT extends AbstractUserRestApiIntegrationTest {

    @Test
    public void getCurrentUserStores_ShouldReturnAllStores() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(userStoresUrl(sessionFilter))
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
        final Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(userStoreUrl(sessionFilter, "store001"))
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("store001"))
            .body("name", equalTo("Real Canadian Superstore"))
            .body("_links.self.href", endsWith("/user/stores/store001"))
            .body("_links.user.href", endsWith("/user"))
            .body("_links.items.href", endsWith("/user/stores/store001/items" + UtilConstants.PAGINATION_TEMPLATES))
            .body("_links.item.href", endsWith("/user/stores/store001/items/{itemId}"))
        ;
    }

}
