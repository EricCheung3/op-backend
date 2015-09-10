package com.openprice.rest;

import static com.jayway.restassured.RestAssured.given;

import javax.inject.Inject;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.filter.session.SessionFilter;
import com.openprice.file.FileSystemService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest(randomPort = true)
@TestExecutionListeners(listeners = {
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public abstract class AbstractRestApiIntegrationTest {

    public static final String USERID_JOHN_DOE = "user001";
    public static final String USERNAME_JOHN_DOE = "john.doe";
    public static final String USERID_JANE_DOE = "user002";
    public static final String USERNAME_JANE_DOE = "jane.doe";
    public static final String USERID_JUNIOR_DOE = "user003";
    public static final String USERNAME_JUNIOR_DOE = "junior.doe";

    @Value("${local.server.port}")
    private int port;

    @Inject
    protected FileSystemService fileSystemService;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    protected SessionFilter login(String username) {
        final SessionFilter sessionFilter = new SessionFilter();

        given()
            .param("username", username)
            .filter(sessionFilter)
        .when()
            .post(UtilConstants.URL_SIGNIN)
        .then()
            .statusCode(HttpStatus.SC_MOVED_TEMPORARILY)
        ;

        return sessionFilter;
    }

}
