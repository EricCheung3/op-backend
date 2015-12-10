package com.openprice.rest.user.store;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.rest.user.AbstractUserRestApiIntegrationTest;
import com.openprice.rest.user.UserApiTestApplication;

@SpringApplicationConfiguration(classes = {UserApiTestApplication.class})
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
                            .item(ShoppingItemForm.builder().name("t-shirt").catalogCode("T SHIRT").build())
                            .item(ShoppingItemForm.builder().name("jean").catalogCode("JEAN").build())
                            .item(ShoppingItemForm.builder().name("shoes").catalogCode("SHOE").build())
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
            .body("_embedded.items[0].name", equalTo("bread"))
            .body("_embedded.items[1].name", equalTo("eggs"))
            .body("_embedded.items[2].name", equalTo("jean"))
            .body("_embedded.items[3].name", equalTo("milk"))
            .body("_embedded.items[4].name", equalTo("shoes"))
            .body("_embedded.items[5].name", equalTo("t-shirt"))
        ;
    }
}
