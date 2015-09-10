package com.openprice.rest.user.store;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

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
public class ShoppingListRestApiIT extends AbstractUserRestApiIntegrationTest {

    @Test
    public void postShoppingList_ShouldAddShoppingItemsForUserStore() {
        final SessionFilter sessionFilter = login(USERNAME_JOHN_DOE);

        final String shoppingListLink =
                given().filter(sessionFilter)
                       .when().get(UtilConstants.API_ROOT + UserApiUrls.URL_USER)
                       .then().extract().path("_links.shoppingList.href");

        final String storeLink =
                given().filter(sessionFilter)
                       .when().get(UtilConstants.API_ROOT + UserApiUrls.URL_USER)
                       .then().extract().path("_links.store.href");
        final String storeUrl =
                UriTemplate.fromTemplate(storeLink)
                           .set("storeId", "store003")
                           .expand();

        final String itemsUrl =
                given().filter(sessionFilter)
                       .when().get(storeUrl)
                       .then().extract().path("_links.items.href");

        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(itemsUrl)
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
        final ShoppingListForm form = new ShoppingListForm();
        form.setStoreId("store003");
        form.getItems().add(new ShoppingListForm.Item("t-shirt", "10.99"));
        form.getItems().add(new ShoppingListForm.Item("jean", "20.99"));
        form.getItems().add(new ShoppingListForm.Item("shoes", "100.99"));

        response =
            given()
                .filter(sessionFilter)
                .contentType(ContentType.JSON)
                .body(form)
            .when()
                .post(shoppingListLink)
            ;

        response
            .then()
            .statusCode(HttpStatus.SC_CREATED)
        ;

        // verify new items
        String updatedItemsUrl = response.getHeader("Location");
        assertEquals(itemsUrl, updatedItemsUrl);

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
