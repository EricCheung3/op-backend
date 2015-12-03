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
import com.openprice.common.ApiConstants;
import com.openprice.domain.store.StoreChain;

public class AdminStoreBranchRestApiIT extends AbstractAdminStoreRestApiIntegrationTest {

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getStoreBranches_ShouldReturnAllBranchesOfStore() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        given()
            .filter(sessionFilter)
        .when()
            .get(storeBranchesUrl(sessionFilter, "chain001"))
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(10))
            .body("page.totalElements", equalTo(3))
            .body("page.totalPages", equalTo(1))
            .body("page.number", equalTo(0))
            .body("_embedded.storeBranches[0].id", equalTo("branch101"))
            .body("_embedded.storeBranches[0].name", equalTo("Calgary Trail RCSS"))
            .body("_embedded.storeBranches[1].id", equalTo("branch102"))
            .body("_embedded.storeBranches[1].name", equalTo("South Common RCSS"))
            .body("_embedded.storeBranches[2].id", equalTo("branch103"))
            .body("_embedded.storeBranches[2].name", equalTo("Whitemud RCSS"))
            .body("_links.self.href", endsWith(URL_ADMIN_CHAINS+"/chain001/branches"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getStoreBranchById_ShouldReturnCorrectStoreBranch() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        given()
            .filter(sessionFilter)
        .when()
            .get(storeBranchUrl(sessionFilter, "chain001", "branch101"))
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("branch101"))
            .body("name", equalTo("Calgary Trail RCSS"))
            .body("phone", equalTo("780-430-2769"))
            .body("_links.self.href", endsWith(URL_ADMIN_CHAINS+"/chain001/branches/branch101"))
            .body("_links.chain.href", endsWith(URL_ADMIN_CHAINS + "/chain001"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getStoreBranchById_ShouldReturn404_WithInvalidId() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        given()
            .filter(sessionFilter)
        .when()
            .get(storeBranchUrl(sessionFilter, "chain001", "invalid"))
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;

        final String invalidStoreUrl = ApiConstants.EXTERNAL_API_ROOT + URL_ADMIN_CHAINS + "/invalid/branches/branch101";
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

        final AdminStoreBranchForm form =
            AdminStoreBranchForm.builder()
                                .name("rcss")
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
                .post(storeBranchesUrl(sessionFilter, "chain001"))
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
            .body("name", equalTo("rcss"))
            .body("address.address1", equalTo("101 456 Street"))
            .body("address.city", equalTo("Edmonton"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void updateStoreBranch_ShouldUpdate() throws Exception {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);
        final String branchUrl = storeBranchUrl(sessionFilter, "chain001", "branch101");
        final AdminStoreBranchForm form =
            AdminStoreBranchForm.builder()
                                .name("New RCSS")
                                .phone("555-123-4567")
                                .address1("101 456 Street")
                                .city("Edmonton")
                                .state("AB")
                                .country("Canada")
                                .build();

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
            .body("name", equalTo("New RCSS"))
            .body("phone", equalTo("555-123-4567"))
            .body("_links.self.href", endsWith(URL_ADMIN_CHAINS+"/chain001/branches/branch101"))
            .body("_links.chain.href", endsWith(URL_ADMIN_CHAINS + "/chain001"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void deleteStoreBranchById_ShouldDeleteBranch() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);
        final String branchUrl = storeBranchUrl(sessionFilter, "chain001", "branch101");

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
        final StoreChain store = storeRepository.findOne("chain001");
        assertEquals(2, storeBranchRepository.findByChain(store).size());
    }


}
