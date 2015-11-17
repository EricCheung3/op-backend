package com.openprice.rest.user.store;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.rest.user.AbstractUserRestApiIntegrationTest;

@DatabaseSetup("classpath:/data/testData.xml")
public class ShoppingItemRestApiIT extends AbstractUserRestApiIntegrationTest {

    @Test
    public void getShoppingItems_ShouldReturnUserShoppingItemsForStore() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(userShoppingItemsUrl(sessionFilter, "shoppingStore101"))
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
            .body("_embedded.shoppingItems[0].id", equalTo("item103"))
            .body("_embedded.shoppingItems[0].name", equalTo("bread"))
            .body("_embedded.shoppingItems[1].id", equalTo("item102"))
            .body("_embedded.shoppingItems[1].name", equalTo("eggs"))
            .body("_embedded.shoppingItems[2].id", equalTo("item101"))
            .body("_embedded.shoppingItems[2].name", equalTo("milk"))
        ;
    }

    @Test
    public void getShoppingListItemById_ShouldReturnShoppingListItem() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        Response response =
        given()
            .filter(sessionFilter)
        .when()
            .get(userShoppingItemUrl(sessionFilter, "shoppingStore101", "item101"))
        ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("item101"))
            .body("name", equalTo("milk"))
            .body("_links.self.href", endsWith("/user/stores/shoppingStore101/items/item101"))
            .body("_links.user.href", endsWith("/user"))
            .body("_links.store.href", endsWith("/user/stores/shoppingStore101"))
        ;
    }

    @Test
    public void deleteShoppingListItemById_ShouldDeleteShoppingListItem() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        final String itemUrl = userShoppingItemUrl(sessionFilter, "shoppingStore101", "item101");

        // first, should get item ok
        given()
            .filter(sessionFilter)
        .when()
            .get(itemUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
        ;

        // delete item
        given()
            .filter(sessionFilter)
        .when()
            .delete(itemUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        // GET should return 404
        given()
            .filter(sessionFilter)
        .when()
            .get(itemUrl)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }
}
