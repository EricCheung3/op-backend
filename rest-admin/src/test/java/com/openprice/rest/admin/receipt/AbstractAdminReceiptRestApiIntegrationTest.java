package com.openprice.rest.admin.receipt;

import static com.jayway.restassured.RestAssured.given;

import javax.inject.Inject;

import com.damnhandy.uri.template.UriTemplate;
import com.jayway.restassured.filter.session.SessionFilter;
import com.openprice.common.ApiConstants;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.rest.admin.AbstractAdminRestApiIntegrationTest;

public abstract class AbstractAdminReceiptRestApiIntegrationTest extends AbstractAdminRestApiIntegrationTest {

    @Inject
    ReceiptRepository receiptRepository;

    @Inject
    ReceiptImageRepository receiptImageRepository;

    protected String receiptsUrl(final SessionFilter sessionFilter) {
        final String receiptsLink =
            given().filter(sessionFilter)
                   .when().get(ApiConstants.EXTERNAL_API_ROOT + URL_ADMIN)
                   .then().extract().path("_links.receipts.href");
        return UriTemplate.fromTemplate(receiptsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String receiptUrl(final SessionFilter sessionFilter, final String receiptId) {
        final String receiptLink =
            given().filter(sessionFilter)
                   .when().get(ApiConstants.EXTERNAL_API_ROOT + URL_ADMIN)
                   .then().extract().path("_links.receipt.href");
        return UriTemplate.fromTemplate(receiptLink).set("receiptId", receiptId).expand();
    }

    protected String receiptImagesUrl(final SessionFilter sessionFilter, final String receiptId) {
        final String imagesLink =
            given().filter(sessionFilter)
                   .when().get(receiptUrl(sessionFilter, receiptId))
                   .then().extract().path("_links.images.href");
        return UriTemplate.fromTemplate(imagesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String receiptImageUrl(final SessionFilter sessionFilter, final String receiptId, final String imageId) {
        final String imageLink =
            given().filter(sessionFilter)
                   .when().get(receiptUrl(sessionFilter, receiptId))
                   .then().extract().path("_links.image.href");
        return UriTemplate.fromTemplate(imageLink).set("imageId", imageId).expand();
    }

    protected String receiptResultsUrl(final SessionFilter sessionFilter, final String receiptId) {
        final String resultsLink =
            given().filter(sessionFilter)
                   .when().get(receiptUrl(sessionFilter, receiptId))
                   .then().extract().path("_links.results.href");
        return UriTemplate.fromTemplate(resultsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String receiptResultUrl(final SessionFilter sessionFilter, final String receiptId, final String resultId) {
        final String resultLink =
            given().filter(sessionFilter)
                   .when().get(receiptUrl(sessionFilter, receiptId))
                   .then().extract().path("_links.result.href");
        return UriTemplate.fromTemplate(resultLink).set("resultId", resultId).expand();
    }

    protected String receiptItemsUrl(final SessionFilter sessionFilter, final String receiptId, final String resultId) {
        final String itemsLink =
            given().filter(sessionFilter)
                   .when().get(receiptResultUrl(sessionFilter, receiptId, resultId))
                   .then().extract().path("_links.items.href");
        return UriTemplate.fromTemplate(itemsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String receiptItemUrl(final SessionFilter sessionFilter, final String receiptId, final String resultId, final String itemId) {
        final String itemLink =
            given().filter(sessionFilter)
                   .when().get(receiptResultUrl(sessionFilter, receiptId, resultId))
                   .then().extract().path("_links.item.href");
        return UriTemplate.fromTemplate(itemLink).set("itemId", itemId).expand();
    }

    protected String receiptFieldsUrl(final SessionFilter sessionFilter, final String receiptId, final String resultId) {
        final String fieldsLink =
                given().filter(sessionFilter)
                       .when().get(receiptResultUrl(sessionFilter, receiptId, resultId))
                       .then().extract().path("_links.fields.href");
        return UriTemplate.fromTemplate(fieldsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String receiptFieldUrl(final SessionFilter sessionFilter, final String receiptId, final String resultId, final String fieldId) {
        final String fieldLink =
                given().filter(sessionFilter)
                       .when().get(receiptResultUrl(sessionFilter, receiptId, resultId))
                       .then().extract().path("_links.field.href");
        return UriTemplate.fromTemplate(fieldLink).set("fieldId", fieldId).expand();
    }

    protected String receiptFeedbacksUrl(final SessionFilter sessionFilter, final String receiptId) {
        final String feedbacksLink =
            given().filter(sessionFilter)
                   .when().get(receiptUrl(sessionFilter, receiptId))
                   .then().extract().path("_links.feedbacks.href");
        return UriTemplate.fromTemplate(feedbacksLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String receiptFeedbackUrl(final SessionFilter sessionFilter, final String receiptId, final String feedbackId) {
        final String feedbackLink =
            given().filter(sessionFilter)
                   .when().get(receiptUrl(sessionFilter, receiptId))
                   .then().extract().path("_links.feedback.href");
        return UriTemplate.fromTemplate(feedbackLink).set("feedbackId", feedbackId).expand();
    }

}
