package com.openprice.rest.admin.receipt;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openprice.domain.account.admin.AdminAccountService;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptFeedback;
import com.openprice.domain.receipt.ReceiptFeedbackRepository;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.domain.receipt.ReceiptService;
import com.openprice.domain.receipt.ReceiptUploadService;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.UtilConstants;

import lombok.extern.slf4j.Slf4j;


/**
 * REST API Controller for global receipt feedback management by admin.
 *
 */
@RestController
@Slf4j
public class AdminReceiptFeedbackRestController extends AbstractReceiptAdminRestController {

    private final ReceiptFeedbackRepository receiptFeedbackRepository;
    private final AdminReceiptFeedbackResource.Assembler receiptFeedbackResourceAssembler;

    @Inject
    public AdminReceiptFeedbackRestController(final AdminAccountService adminAccountService,
                                              final ReceiptService receiptService,
                                              final ReceiptUploadService receiptUploadService,
                                              final ReceiptRepository receiptRepository,
                                              final ReceiptFeedbackRepository receiptFeedbackRepository,
                                              final AdminReceiptFeedbackResource.Assembler receiptFeedbackResourceAssembler) {
        super(adminAccountService, receiptService, receiptUploadService, receiptRepository);
        this.receiptFeedbackRepository = receiptFeedbackRepository;
        this.receiptFeedbackResourceAssembler = receiptFeedbackResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_ADMIN_RECEIPTS_RECEIPT_FEEDBACKS)
    public HttpEntity<PagedResources<AdminReceiptFeedbackResource>> getReceiptFeedbacks(
            @PathVariable("receiptId") final String receiptId,
            @PageableDefault(size = UtilConstants.MAX_RETURN_RECORD_COUNT, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<ReceiptFeedback> assembler) {
        final Receipt receipt = loadReceiptById(receiptId);
        final Page<ReceiptFeedback> feedbacks = receiptFeedbackRepository.findByReceiptOrderByCreatedTime(receipt, pageable);
        return ResponseEntity.ok(assembler.toResource(feedbacks, receiptFeedbackResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_ADMIN_RECEIPTS_RECEIPT_FEEDBACKS_FEEDBACK)
    public HttpEntity<AdminReceiptFeedbackResource> getReceiptFeedbackById(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("feedbackId") final String feedbackId) throws ResourceNotFoundException {
        final Receipt receipt = loadReceiptById(receiptId);
        final ReceiptFeedback feedback = loadReceiptFeedbackByIdAndCheckReceipt(feedbackId, receipt);
        return ResponseEntity.ok(receiptFeedbackResourceAssembler.toResource(feedback));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = URL_ADMIN_RECEIPTS_RECEIPT_FEEDBACKS_FEEDBACK)
    public HttpEntity<Void> deleteReceiptFeedbackById(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("feedbackId") final String feedbackId) throws ResourceNotFoundException {
        final Receipt receipt = loadReceiptById(receiptId);
        final ReceiptFeedback feedback = loadReceiptFeedbackByIdAndCheckReceipt(feedbackId, receipt);
        receiptFeedbackRepository.delete(feedback);
        return ResponseEntity.noContent().build();
    }

    private ReceiptFeedback loadReceiptFeedbackByIdAndCheckReceipt(final String feedbackId, final Receipt receipt) {
        final ReceiptFeedback feedback = receiptFeedbackRepository.findOne(feedbackId);

        if (feedback == null) {
            log.warn("ILLEGAL RECEIPT FEEDBACK ACCESS! No such receipt feedback Id: {}.", feedbackId);
            throw new ResourceNotFoundException("No receipt feedback with the id: " + feedbackId);
        }

        if (!receipt.equals(feedback.getReceipt())) {
            log.warn("ILLEGAL RECEIPT FEEDBACK ACCESS! Feedback '{}' not belong to Receipt '{}'.", feedbackId, receipt.getId());
            throw new ResourceNotFoundException("No receipt feedback with the id: " + feedbackId);
        }

        return feedback;
    }
}
