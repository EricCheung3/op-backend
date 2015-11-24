package com.openprice.rest.admin.store;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.admin.AdminApiUrls;

public class AdminStoreChainRestApiIT extends AbstractAdminStoreRestApiIntegrationTest {

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getStoreChains_ShouldReturnAllStoreChains() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(storeChainsUrl(sessionFilter))
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(10))
            .body("page.totalElements", equalTo(2))
            .body("page.totalPages", equalTo(1))
            .body("page.number", equalTo(0))
            .body("_embedded.storeChains[0].id", equalTo("chain001"))
            .body("_embedded.storeChains[0].code", equalTo("rcss"))
            .body("_embedded.storeChains[1].id", equalTo("chain002"))
            .body("_embedded.storeChains[1].code", equalTo("safeway"))
            .body("_links.self.href", endsWith(AdminApiUrls.URL_ADMIN_CHAINS))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getStoreChainById_ShouldReturnCorrectChain() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(storeChainUrl(sessionFilter, "chain001"))
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("chain001"))
            .body("code", equalTo("rcss"))
            .body("name", equalTo("Real Canadian Superstore"))
            .body("categories", equalTo("Grocery"))
            .body("_links.self.href", endsWith(AdminApiUrls.URL_ADMIN_CHAINS+"/chain001"))
            .body("_links.branches.href", endsWith(AdminApiUrls.URL_ADMIN_CHAINS + "/chain001/branches" + UtilConstants.PAGINATION_TEMPLATES))
            .body("_links.branch.href", endsWith(AdminApiUrls.URL_ADMIN_CHAINS + "/chain001/branches/{branchId}"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getStoreChainById_ShouldReturn404_WithInvalidId() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        given()
            .filter(sessionFilter)
        .when()
            .get(storeChainUrl(sessionFilter, "invalid"))
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void createStoreChain_ShouldCreateNewChain() throws Exception {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        final AdminStoreChainForm form =
            AdminStoreChainForm.builder()
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
                .post(storeChainsUrl(sessionFilter))
            ;

        response
        .then()
            .statusCode(HttpStatus.SC_CREATED)
        ;

        // verify new store chain
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
            .body("code", equalTo("BostonPizza"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void updateStoreChain_ShouldUpdate() throws Exception {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);
        final String chainUrl = storeChainUrl(sessionFilter, "chain001");
        final AdminStoreChainForm form =
            AdminStoreChainForm.builder()
                               .code("rcss")
                               .name("Superstore")
                               .categories("Grocery,Fashion,Pharmacy")
                               .build();

        given()
            .filter(sessionFilter)
            .contentType(ContentType.JSON)
            .body(form)
        .when()
            .put(chainUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        given()
            .filter(sessionFilter)
        .when()
            .get(chainUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("chain001"))
            .body("code", equalTo("rcss"))
            .body("name", equalTo("Superstore"))
            .body("categories", equalTo("Grocery,Fashion,Pharmacy"))
            .body("_links.self.href", endsWith(AdminApiUrls.URL_ADMIN_CHAINS+"/chain001"))
            .body("_links.branches.href", endsWith(AdminApiUrls.URL_ADMIN_CHAINS + "/chain001/branches" + UtilConstants.PAGINATION_TEMPLATES))
            .body("_links.branch.href", endsWith(AdminApiUrls.URL_ADMIN_CHAINS + "/chain001/branches/{branchId}"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void deleteStoreChainById_ShouldDeleteChainAndBranches() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);
        final String chainUrl = storeChainUrl(sessionFilter, "chain001");

        given()
            .filter(sessionFilter)
        .when()
            .delete(chainUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        given()
            .filter(sessionFilter)
        .when()
            .get(chainUrl)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;

        // check store and branch record
        assertNull(storeRepository.findOne("chain001"));
        assertNull(storeBranchRepository.findOne("branch101"));
        assertNull(storeBranchRepository.findOne("branch102"));
        assertNull(storeBranchRepository.findOne("branch103"));
    }

}
