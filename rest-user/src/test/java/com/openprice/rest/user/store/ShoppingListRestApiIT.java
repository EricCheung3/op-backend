package com.openprice.rest.user.store;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.rest.user.AbstractUserRestApiIntegrationTest;

@DatabaseSetup("classpath:/data/testData.xml")
public class ShoppingListRestApiIT extends AbstractUserRestApiIntegrationTest {

    @Test
    public void postShoppingList_ShouldAddShoppingItemsForUserStore() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(userStoreItemsUrl(sessionFilter, "store003"))
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(10))
            .body("page.totalElements", equalTo(0))
            .body("page.totalPages", equalTo(0))
            .body("page.number", equalTo(0))
        ;

        // add shoppinglist
        final ShoppingListForm form =
            ShoppingListForm.builder()
                            .storeId("store003")
                            .item(ShoppingListForm.Item.builder().name("t-shirt").price("10.99").build())
                            .item(ShoppingListForm.Item.builder().name("jean").price("20.99").build())
                            .item(ShoppingListForm.Item.builder().name("shoes").price("100.99").build())
                            .build();

        response =
            given()
                .filter(sessionFilter)
                .contentType(ContentType.JSON)
                .body(form)
            .when()
                .post(userShoppingListUploadUrl(sessionFilter))
            ;
        response
        .then()
            .statusCode(HttpStatus.SC_CREATED)
        ;

        // verify new items
        String updatedItemsUrl = response.getHeader("Location");
        assertEquals(userStoreItemsUrl(sessionFilter, "store003"), updatedItemsUrl);

        response =
            given()
                .filter(sessionFilter)
            .when()
                .get(updatedItemsUrl)
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
            .body("_embedded.shoppingItems[0].itemName", equalTo("jean"))
            .body("_embedded.shoppingItems[0].itemPrice", equalTo("20.99"))
            .body("_embedded.shoppingItems[1].itemName", equalTo("shoes"))
            .body("_embedded.shoppingItems[1].itemPrice", equalTo("100.99"))
            .body("_embedded.shoppingItems[2].itemName", equalTo("t-shirt"))
            .body("_embedded.shoppingItems[2].itemPrice", equalTo("10.99"))
        ;
    }
}
