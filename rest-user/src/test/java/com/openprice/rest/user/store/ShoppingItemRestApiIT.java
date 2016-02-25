package com.openprice.rest.user.store;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.domain.shopping.ShoppingItem;
import com.openprice.domain.shopping.ShoppingItemRepository;
import com.openprice.rest.user.AbstractUserRestApiIntegrationTest;

@DatabaseSetup("classpath:/data/testData.xml")
public class ShoppingItemRestApiIT extends AbstractUserRestApiIntegrationTest {

    @Inject
    private ShoppingItemRepository shoppingItemRepository;

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
            .body("_embedded.shoppingItems[0].number", equalTo(1))
            .body("_embedded.shoppingItems[0].categoryCode", equalTo("bakery"))
            .body("_embedded.shoppingItems[0].catalogCode", equalTo("BREAD"))
            .body("_embedded.shoppingItems[0].catalog", nullValue())
            .body("_embedded.shoppingItems[1].id", equalTo("item102"))
            .body("_embedded.shoppingItems[1].name", equalTo("eggs"))
            .body("_embedded.shoppingItems[1].number", equalTo(1))
            .body("_embedded.shoppingItems[1].categoryCode", equalTo("dairy"))
            .body("_embedded.shoppingItems[1].catalogCode", equalTo("EGG_1235"))
            .body("_embedded.shoppingItems[1].catalog", nullValue())
            .body("_embedded.shoppingItems[2].id", equalTo("item101"))
            .body("_embedded.shoppingItems[2].name", equalTo("milk"))
            .body("_embedded.shoppingItems[2].number", equalTo(1))
            .body("_embedded.shoppingItems[2].categoryCode", equalTo("dairy"))
            .body("_embedded.shoppingItems[2].catalogCode", equalTo("MILK_1234"))
            .body("_embedded.shoppingItems[2].catalog", nullValue())
        ;
    }

    @Test
    public void createShoppingListItem_ShouldAddItem() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        final CreateShoppingItemForm form =
                CreateShoppingItemForm.builder()
                                      .name("Levis Jean")
                                      .catalogCode("CLOTHES")
                                      .build();

        Response response =
        given()
            .filter(sessionFilter)
            .contentType(ContentType.JSON)
            .body(form)
        .when()
            .post(userShoppingItemsUrl(sessionFilter, "shoppingStore101"))
            ;
        response
        .then()
            .statusCode(HttpStatus.SC_CREATED)
        ;

        response =
        given()
            .filter(sessionFilter)
        .when()
            .get(response.getHeader("Location"))
        ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("name", equalTo("Levis Jean"))
            .body("catalogCode", equalTo("CLOTHES"))
            .body("catalog", nullValue())
            .body("_links.user.href", endsWith("/user"))
            .body("_links.store.href", endsWith("/user/stores/shoppingStore101"))
        ;
    }

    @Test
    public void createShoppingListItem_ShouldIncreaseNumber_WhenSameCatalogProduct() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        final CreateShoppingItemForm form =
                CreateShoppingItemForm.builder()
                                      .name("milk")
                                      .catalogCode("MILK_1234")
                                      .build();

        Response response =
        given()
            .filter(sessionFilter)
            .contentType(ContentType.JSON)
            .body(form)
        .when()
            .post(userShoppingItemsUrl(sessionFilter, "shoppingStore101"))
            ;
        response
        .then()
            .statusCode(HttpStatus.SC_CREATED)
        ;

        response =
        given()
            .filter(sessionFilter)
        .when()
            .get(response.getHeader("Location"))
        ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("name", equalTo("milk"))
            .body("catalogCode", equalTo("MILK_1234"))
            .body("number", equalTo(2))
            .body("_links.user.href", endsWith("/user"))
            .body("_links.store.href", endsWith("/user/stores/shoppingStore101"))
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
            .body("number", equalTo(1))
            .body("categoryCode", equalTo("dairy"))
            .body("catalogCode", equalTo("MILK_1234"))
            .body("catalog", nullValue()) // not matching store metadata
            .body("_links.self.href", endsWith("/user/stores/shoppingStore101/items/item101"))
            .body("_links.user.href", endsWith("/user"))
            .body("_links.store.href", endsWith("/user/stores/shoppingStore101"))
        ;
    }

    @Test
    public void updateShoppingListItemById_ShouldUpdateItemNameNumberAndCategory() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        final String itemUrl = userShoppingItemUrl(sessionFilter, "shoppingStore101", "item101");
        final UpdateShoppingItemForm form =
                UpdateShoppingItemForm.builder()
                                      .name("2% milk")
                                      .number(2)
                                      .categoryCode("meat")
                                      .build();

        given()
            .filter(sessionFilter)
            .contentType(ContentType.JSON)
            .body(form)
        .when()
            .put(itemUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        Response response =
        given()
            .filter(sessionFilter)
        .when()
            .get(itemUrl)
        ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("item101"))
            .body("name", equalTo("2% milk"))
            .body("number", equalTo(2))
            .body("categoryCode", equalTo("meat"))
            .body("catalogCode", equalTo("MILK_1234"))
            .body("catalog", nullValue()) // not matching store metadata
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

    @Test
    public void deleteShoppingListItemByAll_ShouldDeleteShoppingListAllItems() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        final String itemsUrl = userShoppingItemsUrl(sessionFilter, "shoppingStore101");

        // first, should get all items ok
        given()
            .filter(sessionFilter)
        .when()
            .get(itemsUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
        ;

        // check total item numbers before delete
        assertEquals(4, shoppingItemRepository.count());

        // delete all items in this store
        given()
            .filter(sessionFilter)
        .when()
            .delete(itemsUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        given()
            .filter(sessionFilter)
        .when()
            .get(itemsUrl)
         .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(10))
            .body("page.totalElements", equalTo(0))
            .body("page.totalPages", equalTo(0))
            .body("page.number", equalTo(0))
        ;
        // check total item numbers after delete
        assertEquals(1, shoppingItemRepository.count());
        assertNull(shoppingItemRepository.findOne("item101"));
        assertNull(shoppingItemRepository.findOne("item102"));
        assertNull(shoppingItemRepository.findOne("item103"));
        ShoppingItem shoppingItem = shoppingItemRepository.findOne("item301");
        assertEquals("bread", shoppingItem.getName());
    }
}
