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
                .get(userStoreItemsUrl(sessionFilter, "store001"))
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
            .body("_embedded.shoppingItems[0].id", equalTo("item003"))
            .body("_embedded.shoppingItems[0].itemName", equalTo("bread"))
            .body("_embedded.shoppingItems[0].itemPrice", equalTo("1.99"))
            .body("_embedded.shoppingItems[1].id", equalTo("item002"))
            .body("_embedded.shoppingItems[1].itemName", equalTo("eggs"))
            .body("_embedded.shoppingItems[1].itemPrice", equalTo("2.99"))
            .body("_embedded.shoppingItems[2].id", equalTo("item001"))
            .body("_embedded.shoppingItems[2].itemName", equalTo("milk"))
            .body("_embedded.shoppingItems[2].itemPrice", equalTo("4.99"))
        ;
    }

    @Test
    public void getShoppingListItemById_ShouldReturnShoppingListItem() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        Response response =
        given()
            .filter(sessionFilter)
        .when()
            .get(userStoreItemUrl(sessionFilter, "store001", "item001"))
        ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("item001"))
            .body("itemName", equalTo("milk"))
            .body("itemPrice", equalTo("4.99"))
            .body("_links.self.href", endsWith("/user/stores/store001/items/item001"))
            .body("_links.user.href", endsWith("/user"))
            .body("_links.store.href", endsWith("/user/stores/store001"))
        ;
    }

    @Test
    public void deleteShoppingListItemById_ShouldDeleteShoppingListItem() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        final String itemUrl = userStoreItemUrl(sessionFilter, "store001", "item001");

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
