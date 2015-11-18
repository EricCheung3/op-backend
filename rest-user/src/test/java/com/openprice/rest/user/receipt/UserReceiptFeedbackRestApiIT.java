package com.openprice.rest.user.receipt;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptFeedback;
import com.openprice.domain.receipt.ReceiptFeedbackRepository;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.rest.user.AbstractUserRestApiIntegrationTest;
import com.openprice.rest.user.UserApiTestApplication;

@SpringApplicationConfiguration(classes = {UserApiTestApplication.class})
@DatabaseSetup("classpath:/data/testData.xml")
//@Ignore
public class UserReceiptFeedbackRestApiIT extends AbstractUserRestApiIntegrationTest {
    @Inject
    private ReceiptRepository receiptRepository;

    @Inject
    private ReceiptFeedbackRepository receiptFeedbackRepository;

    @Test
    public void addReceiptFeedback_ShouldAddFeedbackToReceipt_AndSetNeedFeedbackToFalse() {
        final String RECEIPT_ID = "receipt002";
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        final String receiptUrl = userReceiptUrl(sessionFilter, RECEIPT_ID);
        final String feedbackUrl = userReceiptFeedbackUrl(sessionFilter, RECEIPT_ID);

        given()
            .filter(sessionFilter)
        .when()
            .get(receiptUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo(RECEIPT_ID))
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
            .body("id", equalTo(RECEIPT_ID))
            .body("needFeedback", equalTo(false))
        ;

        // check database
        Receipt receipt = receiptRepository.findOne(RECEIPT_ID);
        assertNotNull(receipt);
        assertFalse(receipt.isNeedFeedback());
        List<ReceiptFeedback> feedbacks = receiptFeedbackRepository.findByReceiptOrderByCreatedTime(receipt);
        assertEquals(1, feedbacks.size());
    }

}
