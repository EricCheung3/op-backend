package com.openprice.rest.admin.store;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.domain.store.StoreChain;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.admin.AdminApiUrls;

public class AdminCatalogRestApiIT extends AbstractAdminStoreRestApiIntegrationTest {

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getCatalogs_ShouldReturnAllCatalogsOfStoreChain() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        given()
            .filter(sessionFilter)
        .when()
            .get(catalogsUrl(sessionFilter, "chain001"))
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(10))
            .body("page.totalElements", equalTo(4))
            .body("page.totalPages", equalTo(1))
            .body("page.number", equalTo(0))
            .body("_embedded.catalogs[0].id", equalTo("chain001cat001"))
            .body("_embedded.catalogs[0].code", equalTo("MILK-1234"))
            .body("_embedded.catalogs[0].name", equalTo("MILK"))
            .body("_embedded.catalogs[0].number", equalTo("1234"))
            .body("_embedded.catalogs[1].id", equalTo("chain001cat002"))
            .body("_embedded.catalogs[1].code", equalTo("EGG-1235"))
            .body("_embedded.catalogs[1].name", equalTo("EGG"))
            .body("_embedded.catalogs[1].number", equalTo("1235"))
            .body("_embedded.catalogs[2].id", equalTo("chain001cat003"))
            .body("_embedded.catalogs[2].code", equalTo("PORK"))
            .body("_embedded.catalogs[2].name", equalTo("PORK"))
            .body("_embedded.catalogs[3].id", equalTo("chain001cat004"))
            .body("_embedded.catalogs[3].code", equalTo("BREAD"))
            .body("_embedded.catalogs[3].name", equalTo("BREAD"))
            .body("_links.self.href", endsWith(AdminApiUrls.URL_ADMIN_CHAINS+"/chain001/catalogs"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getCatalogById_ShouldReturnCorrectCatalog() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        given()
            .filter(sessionFilter)
        .when()
            .get(catalogUrl(sessionFilter, "chain001", "chain001cat001"))
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("chain001cat001"))
            .body("name", equalTo("MILK"))
            .body("number", equalTo("1234"))
            .body("_links.self.href", endsWith(AdminApiUrls.URL_ADMIN_CHAINS+"/chain001/catalogs/chain001cat001"))
            .body("_links.chain.href", endsWith(AdminApiUrls.URL_ADMIN_CHAINS + "/chain001"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getCatalogById_ShouldReturn404_WithInvalidId() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        given()
            .filter(sessionFilter)
        .when()
            .get(catalogUrl(sessionFilter, "chain001", "invalid"))
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;

        final String invalidStoreUrl = UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_CHAINS + "/invalid/catalogs/chain001cat001";
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
        final AdminCatalogForm form =
            AdminCatalogForm.builder()
                            .name("APPLE")
                            .number("3333")
                            .category("FRUIT")
                            .price("1.99")
                            .naturalName("Apple")
                            .labelCodes("fruit,apple")
                            .build();

        Response response =
            given()
                .filter(sessionFilter)
                .contentType(ContentType.JSON)
                .body(form)
            .when()
                .post(catalogsUrl(sessionFilter, "chain001"))
            ;

        response
        .then()
            .statusCode(HttpStatus.SC_CREATED)
        ;

        // verify new catalog
        final String catalogUrl = response.getHeader("Location");

        response =
            given()
                .filter(sessionFilter)
            .when()
                .get(catalogUrl)
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("code", equalTo("APPLE-3333"))
            .body("name", equalTo("APPLE"))
            .body("number", equalTo("3333"))
            .body("price", equalTo("1.99"))
            .body("_links.chain.href", endsWith(AdminApiUrls.URL_ADMIN_CHAINS + "/chain001"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void updateCatalog_ShouldUpdate() throws Exception {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);
        final String catalogUrl = catalogUrl(sessionFilter, "chain001", "chain001cat001");
        final AdminCatalogForm form =
                AdminCatalogForm.builder()
                                .name("APPLE")
                                .number("3333")
                                .category("FRUIT")
                                .price("1.99")
                                .naturalName("Apple")
                                .labelCodes("fruit,apple")
                                .build();

        given()
            .filter(sessionFilter)
            .contentType(ContentType.JSON)
            .body(form)
        .when()
            .put(catalogUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        given()
            .filter(sessionFilter)
        .when()
            .get(catalogUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("chain001cat001"))
            .body("code", equalTo("APPLE-3333"))
            .body("name", equalTo("APPLE"))
            .body("number", equalTo("3333"))
            .body("_links.self.href", endsWith(AdminApiUrls.URL_ADMIN_CHAINS+"/chain001/catalogs/chain001cat001"))
            .body("_links.chain.href", endsWith(AdminApiUrls.URL_ADMIN_CHAINS + "/chain001"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void deleteCatalogById_ShouldDeleteCatalog() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);
        final String catalogUrl = catalogUrl(sessionFilter, "chain001", "chain001cat001");

        given()
            .filter(sessionFilter)
        .when()
            .delete(catalogUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        given()
            .filter(sessionFilter)
        .when()
            .get(catalogUrl)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;

        // check catalog record
        assertNull(catalogRepository.findOne("chain001cat001"));
        final StoreChain store = storeRepository.findOne("chain001");
        assertEquals(3, storeBranchRepository.findByChain(store).size());
    }

}
