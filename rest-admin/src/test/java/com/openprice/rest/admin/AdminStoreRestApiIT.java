package com.openprice.rest.admin;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.damnhandy.uri.template.UriTemplate;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.domain.store.Store;
import com.openprice.domain.store.StoreBranch;
import com.openprice.domain.store.StoreBranchRepository;
import com.openprice.domain.store.StoreRepository;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.admin.store.AdminStoreForm;

public class AdminStoreRestApiIT extends AbstractAdminRestApiIntegrationTest {
    @Inject
    private StoreRepository storeRepository;

    @Inject
    private StoreBranchRepository storeBranchRepository;

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getStores_ShouldReturnAllStores() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        given()
            .filter(sessionFilter)
        .when()
            .get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_STORES)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(10))
            .body("page.totalElements", equalTo(2))
            .body("page.totalPages", equalTo(1))
            .body("page.number", equalTo(0))
            .body("_embedded.stores[0].id", equalTo("store001"))
            .body("_embedded.stores[0].code", equalTo("RCSS"))
            .body("_embedded.stores[1].id", equalTo("store002"))
            .body("_embedded.stores[1].code", equalTo("Safeway"))
            .body("_links.self.href", endsWith(AdminApiUrls.URL_ADMIN_STORES))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getStoreById_ShouldReturnCorrectStore() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        given()
            .filter(sessionFilter)
        .when()
            .get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_STORES_STORE, "store001")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("store001"))
            .body("code", equalTo("RCSS"))
            .body("name", equalTo("Real Canadian Superstore"))
            .body("categories", equalTo("Grocery"))
            .body("_links.self.href", endsWith(AdminApiUrls.URL_ADMIN_STORES+"/store001"))
            .body("_links.branches.href", endsWith(AdminApiUrls.URL_ADMIN_STORES + "/store001/branches" + UtilConstants.PAGINATION_TEMPLATES))
            .body("_links.branch.href", endsWith(AdminApiUrls.URL_ADMIN_STORES + "/store001/branches/{branchId}"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getStoreById_ShouldReturn404_WithInvalidId() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        given()
            .filter(sessionFilter)
        .when()
            .get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_STORES_STORE, "invalid")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void createStore_ShouldCreateNewStore() throws Exception {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        final AdminStoreForm form =
                AdminStoreForm.builder()
                              .code("BostonPizza")
                              .name("Boston Pizza")
                              .categories("Restaurant")
                              .build();

        String storesLink =
                given().filter(sessionFilter)
                       .when().get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN)
                       .then().extract().path("_links.stores.href");
        String storesUrl =  UriTemplate.fromTemplate(storesLink)
                                       .set("page", null)
                                       .set("size", null)
                                       .set("sort", null)
                                       .expand();

        Response response =
            given()
                .filter(sessionFilter)
                .contentType(ContentType.JSON)
                .body(form)
            .when()
                .post(storesUrl)
            ;

        response
        .then()
            .statusCode(HttpStatus.SC_CREATED)
        ;

        // verify new store
        String storeUrl = response.getHeader("Location");

        response =
            given()
                .filter(sessionFilter)
            .when()
                .get(storeUrl)
            ;
        response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("code", equalTo("BostonPizza"))
        ;

    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void updateStore_ShouldUpdate() throws Exception {
        final Store store001 = storeRepository.findOne("store001");
        final AdminStoreForm form = new AdminStoreForm(store001);
        final String storeUrl = UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_STORES + "/store001";
        form.setName("Superstore");

        form.setCategories("Grocery,Fashion,Pharmacy");

        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);
        given()
            .filter(sessionFilter)
            .contentType(ContentType.JSON)
            .body(form)
        .when()
            .put(storeUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        given()
            .filter(sessionFilter)
        .when()
            .get(storeUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("store001"))
            .body("code", equalTo("RCSS"))
            .body("name", equalTo("Superstore"))
            .body("categories", equalTo("Grocery,Fashion,Pharmacy"))
            .body("_links.self.href", endsWith(AdminApiUrls.URL_ADMIN_STORES+"/store001"))
            .body("_links.branches.href", endsWith(AdminApiUrls.URL_ADMIN_STORES + "/store001/branches" + UtilConstants.PAGINATION_TEMPLATES))
            .body("_links.branch.href", endsWith(AdminApiUrls.URL_ADMIN_STORES + "/store001/branches/{branchId}"))
        ;

    }

    @Test
    public void deleteStoreById_ShouldDeleteStoreAndBranches() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        String storeLink =
                given().filter(sessionFilter)
                       .when().get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN)
                       .then().extract().path("_links.store.href");
        String storeUrl =
                UriTemplate.fromTemplate(storeLink)
                           .set("storeId", "store001")
                           .expand();

        given()
            .filter(sessionFilter)
        .when()
            .delete(storeUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        given()
            .filter(sessionFilter)
        .when()
            .get(storeUrl)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;

        // check store and branch record
        Store store = storeRepository.findOne("store001");
        assertNull(store);
        StoreBranch branch = storeBranchRepository.findOne("branch001");
        assertNull(branch);
    }

}
