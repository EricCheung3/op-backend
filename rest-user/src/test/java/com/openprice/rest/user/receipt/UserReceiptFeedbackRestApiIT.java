package com.openprice.rest.user.receipt;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptFeedback;
import com.openprice.domain.receipt.ReceiptFeedbackRepository;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.rest.user.AbstractUserRestApiIntegrationTest;

@DatabaseSetup("classpath:/data/test-data.xml")
public class UserReceiptFeedbackRestApiIT extends AbstractUserRestApiIntegrationTest {

    @Inject
    private ReceiptRepository receiptRepository;

    @Inject
    private ReceiptFeedbackRepository receiptFeedbackRepository;

    private String TEST_RECEIPT_ID;
    private String TEST_RECEIPT_ID_INVALID;
    private SessionFilter sessionFilter;
    private SessionFilter sessionFilter2;
    private String receiptUrl;

    @Before
    public void setup() {
        TEST_RECEIPT_ID = "receipt002";
        TEST_RECEIPT_ID_INVALID = "invalid";
        sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        sessionFilter2 = login(TEST_USERNAME_JANE_DOE);
        receiptUrl = userReceiptUrl(sessionFilter, TEST_RECEIPT_ID);
    }

    @Test
    public void addReceiptFeedback_ShouldAddFeedbackToReceipt_AndSetNeedFeedbackToFalse() {
        final String feedbackUrl = userReceiptFeedbackUrl(sessionFilter, TEST_RECEIPT_ID);

        given()
            .filter(sessionFilter)
        .when()
            .get(receiptUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo(TEST_RECEIPT_ID))
            .body("needFeedback", equalTo(true))
        ;

        final FeedbackForm form = new FeedbackForm(4,"Very good!");

        given()
            .filter(sessionFilter)
            .contentType(ContentType.JSON)
            .body(form)
        .when()
            .post(feedbackUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        given()
            .filter(sessionFilter)
        .when()
            .get(receiptUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo(TEST_RECEIPT_ID))
            .body("needFeedback", equalTo(false))
        ;

        // check database
        Receipt receipt = receiptRepository.findOne(TEST_RECEIPT_ID);
        assertNotNull(receipt);
        assertFalse(receipt.getNeedFeedback());
        List<ReceiptFeedback> feedbacks = receiptFeedbackRepository.findByReceiptOrderByCreatedTime(receipt);
        assertEquals(1, feedbacks.size());
    }

    @Test
    public void addReceiptFeedback_ShouldReturn404NotFound_WhenInvalidReceiptId() {
        final String receiptUrl = userReceiptUrl(sessionFilter, TEST_RECEIPT_ID_INVALID);
        given()
            .filter(sessionFilter)
        .when()
            .get(receiptUrl)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;

        // check database
        Receipt receipt = receiptRepository.findOne(TEST_RECEIPT_ID_INVALID);
        assertNull(receipt);
    }

    @Test
    public void addReceiptFeedback_ShouldReurn403Forbidden_WhenReceiptNotBelongToCurrentLoginUser() {

        given()
            .filter(sessionFilter2)
        .when()
            .get(receiptUrl)
        .then()
            .statusCode(HttpStatus.SC_FORBIDDEN)
        ;

        // check database
        Receipt receipt = receiptRepository.findOne(TEST_RECEIPT_ID);
        assertTrue(receipt.getNeedFeedback());
    }

}
