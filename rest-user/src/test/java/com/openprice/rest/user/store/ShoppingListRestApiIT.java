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
    public void postShoppingList_ShouldAddShoppingItemsForExistingShoppingStore() {
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
        ;

        // add shoppinglist
        final ShoppingListForm form =
            ShoppingListForm.builder()
                            .chainCode("rcss")
                            .item(CreateShoppingItemForm.builder().name("t-shirt").catalogCode("T SHIRT").build())
                            .item(CreateShoppingItemForm.builder().name("jean").catalogCode("JEAN").build())
                            .item(CreateShoppingItemForm.builder().name("shoes").catalogCode("SHOE").build())
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
        String updatedStoreUrl = response.getHeader("Location");
        assertEquals(userShoppingStoreUrl(sessionFilter, "shoppingStore101"), updatedStoreUrl);

        response =
            given()
                .filter(sessionFilter)
            .when()
                .get(updatedStoreUrl)
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("shoppingStore101"))
            .body("chainCode", equalTo("rcss"))
            .body("displayName", equalTo("Superstore"))
            .body("_embedded.shoppingItems[0].name", equalTo("bread"))
            .body("_embedded.shoppingItems[0].catalogCode", equalTo("BREAD"))
            .body("_embedded.shoppingItems[0].productCategory", equalTo("bakery"))
            .body("_embedded.shoppingItems[0].number", equalTo(1))
            .body("_embedded.shoppingItems[1].name", equalTo("eggs"))
            .body("_embedded.shoppingItems[1].catalogCode", equalTo("EGG_1235"))
            .body("_embedded.shoppingItems[1].productCategory", equalTo("dairy"))
            .body("_embedded.shoppingItems[1].number", equalTo(1))
            .body("_embedded.shoppingItems[2].name", equalTo("jean"))
            .body("_embedded.shoppingItems[2].catalogCode", equalTo("JEAN"))
            .body("_embedded.shoppingItems[2].productCategory", equalTo("uncategorized"))
            .body("_embedded.shoppingItems[2].number", equalTo(1))
            .body("_embedded.shoppingItems[3].name", equalTo("milk"))
            .body("_embedded.shoppingItems[3].catalogCode", equalTo("MILK_1234"))
            .body("_embedded.shoppingItems[3].productCategory", equalTo("dairy"))
            .body("_embedded.shoppingItems[3].number", equalTo(1))
            .body("_embedded.shoppingItems[4].name", equalTo("shoes"))
            .body("_embedded.shoppingItems[4].catalogCode", equalTo("SHOE"))
            .body("_embedded.shoppingItems[4].productCategory", equalTo("uncategorized"))
            .body("_embedded.shoppingItems[4].number", equalTo(1))
            .body("_embedded.shoppingItems[5].name", equalTo("t-shirt"))
            .body("_embedded.shoppingItems[5].catalogCode", equalTo("T SHIRT"))
            .body("_embedded.shoppingItems[5].productCategory", equalTo("uncategorized"))
            .body("_embedded.shoppingItems[5].number", equalTo(1))

        ;
    }
}
