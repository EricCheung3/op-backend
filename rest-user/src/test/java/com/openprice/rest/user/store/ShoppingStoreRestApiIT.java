package com.openprice.rest.user.store;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
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
import com.openprice.domain.shopping.ShoppingStore;
import com.openprice.domain.shopping.ShoppingStoreRepository;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.user.AbstractUserRestApiIntegrationTest;

@DatabaseSetup("classpath:/data/test-data.xml")
public class ShoppingStoreRestApiIT extends AbstractUserRestApiIntegrationTest {

    @Inject
    ShoppingStoreRepository shoppingStoreRepository;

    @Inject
    ShoppingItemRepository shoppingItemRepository;

    @Test
    public void getCurrentUserShoppingStores_ShouldReturnAllUserShoppingStores() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(userShoppingStoresUrl(sessionFilter))
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
            .body("_embedded.shoppingStores[0].id", equalTo("shoppingStore102"))
            .body("_embedded.shoppingStores[0].chainCode", equalTo("safeway"))
            .body("_embedded.shoppingStores[0].icon", equalTo("safeway"))
            .body("_embedded.shoppingStores[0].displayName", equalTo("Safeway"))
            .body("_embedded.shoppingStores[1].id", equalTo("shoppingStore101"))
            .body("_embedded.shoppingStores[1].chainCode", equalTo("rcss"))
            .body("_embedded.shoppingStores[1].icon", equalTo("rcss"))
            .body("_embedded.shoppingStores[2].id", equalTo("shoppingStore103"))
            .body("_embedded.shoppingStores[2].displayName", equalTo("Unknown"))
        ;
    }

    @Test
    public void getUserShoppingStoreById_ShouldReturnStore() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        final Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(userShoppingStoreUrl(sessionFilter, "shoppingStore101"))
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("shoppingStore101"))
            .body("chainCode", equalTo("rcss"))
            .body("icon", equalTo("rcss"))
            .body("displayName", equalTo("Superstore"))
            .body("_embedded.shoppingItems[0].id", equalTo("item103"))
            .body("_embedded.shoppingItems[0].catalogCode", equalTo("BREAD"))
            .body("_embedded.shoppingItems[0].name", equalTo("bread"))
            .body("_embedded.shoppingItems[0].categoryCode", equalTo("bakery"))
            .body("_embedded.shoppingItems[1].id", equalTo("item102"))
            .body("_embedded.shoppingItems[1].catalogCode", equalTo("EGG_1235"))
            .body("_embedded.shoppingItems[1].name", equalTo("eggs"))
            .body("_embedded.shoppingItems[1].categoryCode", equalTo("dairy"))
            .body("_embedded.shoppingItems[2].id", equalTo("item101"))
            .body("_embedded.shoppingItems[2].catalogCode", equalTo("MILK_1234"))
            .body("_embedded.shoppingItems[2].name", equalTo("milk"))
            .body("_embedded.shoppingItems[2].categoryCode", equalTo("dairy"))
            .body("_links.self.href", endsWith("/user/stores/shoppingStore101"))
            .body("_links.user.href", endsWith("/user"))
            .body("_links.items.href", endsWith("/user/stores/shoppingStore101/items" + UtilConstants.PAGINATION_TEMPLATES))
            .body("_links.item.href", endsWith("/user/stores/shoppingStore101/items/{itemId}"))
        ;
    }

    @Test
    public void searchCatalogsForStoreChain_ShouldReturnMatchingCatalogFromRcss() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        final Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(userShoppingStoreCatalogsQueryUrl(sessionFilter, "shoppingStore101", "egg"))
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("[0].categoryCode", equalTo("dairy"))
        ;

    }

    @Test
    public void searchCatalogsForStoreChain_ShouldReturnEmpty_IfQueryEmpty() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        final Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(userShoppingStoreCatalogsQueryUrl(sessionFilter, "shoppingStore101", ""))
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("", hasSize(0))
        ;
    }

    @Test
    public void deleteUserShoppingStoreById_ShouldDeleteShoppingStoreAndItems() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        final String shoppingStoreUrl = userShoppingStoreUrl(sessionFilter, "shoppingStore103");

        given()
            .filter(sessionFilter)
        .when()
            .delete(shoppingStoreUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        given()
            .filter(sessionFilter)
        .when()
            .get(shoppingStoreUrl)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;

        // check shopping store and item record
        ShoppingStore store = shoppingStoreRepository.findOne("shoppingStore103");
        assertNull(store);
        ShoppingItem item = shoppingItemRepository.findOne("item301");
        assertNull(item);
    }
}
