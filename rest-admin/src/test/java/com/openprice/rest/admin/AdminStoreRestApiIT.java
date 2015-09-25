package com.openprice.rest.admin;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
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
import com.openprice.rest.admin.store.AdminStoreBranchForm;
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
        final String storesUrl = getStoresUrl(sessionFilter);

        given()
            .filter(sessionFilter)
        .when()
            .get(storesUrl)
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
        final String storeUrl = getStoreUrl(sessionFilter, "store001");

        given()
            .filter(sessionFilter)
        .when()
            .get(storeUrl)
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
        final String storeUrl = getStoreUrl(sessionFilter, "invalid");

        given()
            .filter(sessionFilter)
        .when()
            .get(storeUrl)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void createStore_ShouldCreateNewStore() throws Exception {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);
        final String storesUrl = getStoresUrl(sessionFilter);

        final AdminStoreForm form =
            AdminStoreForm.builder()
                          .code("BostonPizza")
                          .name("Boston Pizza")
                          .categories("Restaurant")
                          .build();

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
        final String storeUrl = response.getHeader("Location");

        response =
            given()
                .filter(sessionFilter)
            .when()
                .get(storeUrl)
            ;
        //response.prettyPrint();
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
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);
        final String storeUrl = getStoreUrl(sessionFilter, "store001");
        final AdminStoreForm form = new AdminStoreForm(store001);
        form.setName("Superstore");
        form.setCategories("Grocery,Fashion,Pharmacy");

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
        final String storeUrl = getStoreUrl(sessionFilter, "store001");

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
        assertNull(storeRepository.findOne("store001"));
        assertNull(storeBranchRepository.findOne("branch101"));
        assertNull(storeBranchRepository.findOne("branch102"));
        assertNull(storeBranchRepository.findOne("branch103"));
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getStoreBranches_ShouldReturnAllBranchesOfStore() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);
        final String branchesUrl = getStoreBranchesUrl(sessionFilter, "store001");

        given()
            .filter(sessionFilter)
        .when()
            .get(branchesUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(10))
            .body("page.totalElements", equalTo(3))
            .body("page.totalPages", equalTo(1))
            .body("page.number", equalTo(0))
            .body("_embedded.storeBranches[0].id", equalTo("branch101"))
            .body("_embedded.storeBranches[0].name", equalTo("RCSS"))
            .body("_embedded.storeBranches[1].id", equalTo("branch102"))
            .body("_embedded.storeBranches[1].name", equalTo("RCSS"))
            .body("_embedded.storeBranches[2].id", equalTo("branch103"))
            .body("_embedded.storeBranches[2].name", equalTo("RCSS"))
            .body("_links.self.href", endsWith(AdminApiUrls.URL_ADMIN_STORES+"/store001/branches"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getStoreBranchById_ShouldReturnCorrectStoreBranch() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);
        final String storeBranchUrl = getStoreBranchUrl(sessionFilter, "store001", "branch101");

        given()
            .filter(sessionFilter)
        .when()
            .get(storeBranchUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("branch101"))
            .body("name", equalTo("RCSS"))
            .body("phone", equalTo("780-430-2769"))
            .body("_links.self.href", endsWith(AdminApiUrls.URL_ADMIN_STORES+"/store001/branches/branch101"))
            .body("_links.store.href", endsWith(AdminApiUrls.URL_ADMIN_STORES + "/store001"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getStoreBranchById_ShouldReturn404_WithInvalidId() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);
        final String storeBranchUrl = getStoreBranchUrl(sessionFilter, "store001", "invalid");

        given()
            .filter(sessionFilter)
        .when()
            .get(storeBranchUrl)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;

        final String invalidStoreUrl = UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_STORES + "/invalid/branches/branch101";
        given()
            .filter(sessionFilter)
        .when()
            .get(invalidStoreUrl)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void createStoreBranch_ShouldCreateNewBranchForStore() throws Exception {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);
        final String branchesUrl = getStoreBranchesUrl(sessionFilter, "store001");

        final AdminStoreBranchForm form =
            AdminStoreBranchForm.builder()
                                .name("RCSS")
                                .phone("780-123-4567")
                                .address1("101 456 Street")
                                .city("Edmonton")
                                .state("AB")
                                .country("Canada")
                                .build();

        Response response =
            given()
                .filter(sessionFilter)
                .contentType(ContentType.JSON)
                .body(form)
            .when()
                .post(branchesUrl)
            ;

        response
        .then()
            .statusCode(HttpStatus.SC_CREATED)
        ;

        // verify new branch
        final String branchUrl = response.getHeader("Location");

        response =
            given()
                .filter(sessionFilter)
            .when()
                .get(branchUrl)
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("name", equalTo("RCSS"))
            .body("address.address1", equalTo("101 456 Street"))
            .body("address.city", equalTo("Edmonton"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void updateStoreBranch_ShouldUpdate() throws Exception {
        final StoreBranch branch101 = storeBranchRepository.findOne("branch101");
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);
        final String branchUrl = getStoreBranchUrl(sessionFilter, "store001", "branch101");
        final AdminStoreBranchForm form = new AdminStoreBranchForm(branch101);
        form.setName("Superstore");
        form.setPhone("555-123-4567");

        given()
            .filter(sessionFilter)
            .contentType(ContentType.JSON)
            .body(form)
        .when()
            .put(branchUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        given()
            .filter(sessionFilter)
        .when()
            .get(branchUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("branch101"))
            .body("name", equalTo("Superstore"))
            .body("phone", equalTo("555-123-4567"))
            .body("_links.self.href", endsWith(AdminApiUrls.URL_ADMIN_STORES+"/store001/branches/branch101"))
            .body("_links.store.href", endsWith(AdminApiUrls.URL_ADMIN_STORES + "/store001"))
        ;
    }

    @Test
    public void deleteStoreBranchById_ShouldDeleteBranch() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);
        final String branchUrl = getStoreBranchUrl(sessionFilter, "store001", "branch101");

        given()
            .filter(sessionFilter)
        .when()
            .delete(branchUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        given()
            .filter(sessionFilter)
        .when()
            .get(branchUrl)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;

        // check branch record
        assertNull(storeBranchRepository.findOne("branch101"));
        final Store store = storeRepository.findOne("store001");
        assertEquals(2, storeBranchRepository.findByStore(store).size());
    }

    private String getStoresUrl(final SessionFilter sessionFilter) {
        final String storesLink =
            given().filter(sessionFilter)
                   .when().get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN)
                   .then().extract().path("_links.stores.href");
        return UriTemplate.fromTemplate(storesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String getStoreUrl(final SessionFilter sessionFilter, final String storeId) {
        final String storeLink =
            given().filter(sessionFilter)
                   .when().get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN)
                   .then().extract().path("_links.store.href");
        return UriTemplate.fromTemplate(storeLink).set("storeId", storeId).expand();
    }

    private String getStoreBranchesUrl(final SessionFilter sessionFilter, final String storeId) {
        final String storeUrl = getStoreUrl(sessionFilter, storeId);
        final String branchesLink =
            given().filter(sessionFilter)
                   .when().get(storeUrl)
                   .then().extract().path("_links.branches.href");
        return UriTemplate.fromTemplate(branchesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String getStoreBranchUrl(final SessionFilter sessionFilter, final String storeId, final String branchId) {
        final String storeUrl = getStoreUrl(sessionFilter, storeId);
        final String branchLink =
            given().filter(sessionFilter)
                   .when().get(storeUrl)
                   .then().extract().path("_links.branch.href");
        return UriTemplate.fromTemplate(branchLink).set("branchId", branchId).expand();
    }
}
