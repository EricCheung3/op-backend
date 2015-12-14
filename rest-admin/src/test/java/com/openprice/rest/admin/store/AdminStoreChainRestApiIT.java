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

@DatabaseSetup("classpath:/data/testAdmin.xml")
public class AdminStoreChainRestApiIT extends AbstractAdminStoreRestApiIntegrationTest {

    @Test
    public void getStoreChains_ShouldReturnAllStoreChains() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);

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
            .body("_links.self.href", endsWith(URL_ADMIN_CHAINS))
        ;
    }

    @Test
    public void getStoreChainById_ShouldReturnCorrectChain() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);

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
            .body("catalogUploadUrl", endsWith("api/admin/chains/chain001/catalogs/upload"))
            .body("_links.self.href", endsWith(URL_ADMIN_CHAINS+"/chain001"))
            .body("_links.branches.href", endsWith(URL_ADMIN_CHAINS + "/chain001/branches" + UtilConstants.PAGINATION_TEMPLATES))
            .body("_links.branch.href", endsWith(URL_ADMIN_CHAINS + "/chain001/branches/{branchId}"))
        ;
    }

    @Test
    public void getStoreChainById_ShouldReturn404_WithInvalidId() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);

        given()
            .filter(sessionFilter)
        .when()
            .get(storeChainUrl(sessionFilter, "invalid"))
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }

    @Test
    public void createStoreChain_ShouldCreateNewChain() throws Exception {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);

        final AdminStoreChainForm form =
            AdminStoreChainForm.builder()
                               .code("BostonPizza")
                               .name("Boston Pizza")
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
    public void updateStoreChain_ShouldUpdate() throws Exception {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);
        final String chainUrl = storeChainUrl(sessionFilter, "chain001");
        final AdminStoreChainForm form =
            AdminStoreChainForm.builder()
                               .code("rcss")
                               .name("Superstore")
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
            .body("_links.self.href", endsWith(URL_ADMIN_CHAINS+"/chain001"))
            .body("_links.branches.href", endsWith(URL_ADMIN_CHAINS + "/chain001/branches" + UtilConstants.PAGINATION_TEMPLATES))
            .body("_links.branch.href", endsWith(URL_ADMIN_CHAINS + "/chain001/branches/{branchId}"))
        ;
    }

    @Test
    public void deleteStoreChainById_ShouldDeleteChainAndBranches() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);
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
