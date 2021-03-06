package com.openprice.rest.admin.receipt;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;

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
import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.domain.receipt.ReceiptItemRepository;
import com.openprice.domain.receipt.ReceiptParsingService;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.domain.receipt.ReceiptResult;
import com.openprice.domain.receipt.ReceiptResultRepository;
import com.openprice.domain.receipt.ReceiptService;
import com.openprice.domain.receipt.ReceiptUploadService;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.UtilConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * REST API Controller for global receipt parser result management by admin.
 *
 */
@RestController
@Slf4j
public class AdminReceiptResultRestController extends AbstractReceiptAdminRestController {

    private final ReceiptResultRepository receiptResultRepository;
    private final ReceiptItemRepository receiptItemRepository;
    private final ReceiptParsingService receiptParsingService;
    private final AdminReceiptResultResource.Assembler receiptResultResourceAssembler;
    private final AdminReceiptItemResource.Assembler receiptItemResourceAssembler;

    @Inject
    public AdminReceiptResultRestController(final AdminAccountService adminAccountService,
                                            final ReceiptService receiptService,
                                            final ReceiptUploadService receiptUploadService,
                                            final ReceiptRepository receiptRepository,
                                            final ReceiptResultRepository receiptResultRepository,
                                            final ReceiptItemRepository receiptItemRepository,
                                            final ReceiptParsingService receiptParsingService,
                                            final AdminReceiptResultResource.Assembler receiptResultResourceAssembler,
                                            final AdminReceiptItemResource.Assembler receiptItemResourceAssembler) {
        super(adminAccountService, receiptService, receiptUploadService, receiptRepository);
        this.receiptResultRepository = receiptResultRepository;
        this.receiptItemRepository = receiptItemRepository;
        this.receiptParsingService = receiptParsingService;
        this.receiptResultResourceAssembler = receiptResultResourceAssembler;
        this.receiptItemResourceAssembler = receiptItemResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_ADMIN_RECEIPTS_RECEIPT_RESULTS)
    public HttpEntity<PagedResources<AdminReceiptResultResource>> getReceiptResults(
            @PathVariable("receiptId") final String receiptId,
            @PageableDefault(size = UtilConstants.MAX_RETURN_RECORD_COUNT, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<ReceiptResult> assembler) {
        final Receipt receipt = loadReceiptById(receiptId);
        final Page<ReceiptResult> results = receiptResultRepository.findByReceiptOrderByCreatedTime(receipt, pageable);
        return ResponseEntity.ok(assembler.toResource(results, receiptResultResourceAssembler));
    }

    /**
     * Re-call the parser to parse receipt again by Admin
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = URL_ADMIN_RECEIPTS_RECEIPT_RESULTS)
    public HttpEntity<Void> addParserResultByReceiptId(
            @PathVariable("receiptId") final String receiptId) throws ResourceNotFoundException {
        final Receipt receipt = loadReceiptById(receiptId);

        final ReceiptResult result = receiptParsingService.reparseReceiptImageOcrText(receipt);
        if (result != null) {
            final URI location = linkTo(methodOn(AdminReceiptResultRestController.class).getReceiptResultById(receiptId, result.getId())).toUri();
            return ResponseEntity.created(location).build();
        }else {
            log.error("SEVERE: No parse result!");
            return null;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT)
    public HttpEntity<AdminReceiptResultResource> getReceiptResultById(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("resultId") final String resultId) throws ResourceNotFoundException {
        final Receipt receipt = loadReceiptById(receiptId);
        final ReceiptResult result = loadReceiptResultByIdAndCheckReceipt(resultId, receipt);
        return ResponseEntity.ok(receiptResultResourceAssembler.toResource(result));
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT_ITEMS)
    public HttpEntity<PagedResources<AdminReceiptItemResource>> getReceiptResultItems(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("resultId") final String resultId,
            @PageableDefault(size = UtilConstants.MAX_RETURN_RECORD_COUNT, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<ReceiptItem> assembler) throws ResourceNotFoundException {
        final Receipt receipt = loadReceiptById(receiptId);
        final ReceiptResult result = loadReceiptResultByIdAndCheckReceipt(resultId, receipt);
        final Page<ReceiptItem> items = receiptItemRepository.findByReceiptResultOrderByLineNumber(result, pageable);
        return ResponseEntity.ok(assembler.toResource(items, receiptItemResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT_ITEMS_ITEM)
    public HttpEntity<AdminReceiptItemResource> getReceiptResultItemById(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("resultId") final String resultId,
            @PathVariable("itemId") final String itemId) throws ResourceNotFoundException {
        final Receipt receipt = loadReceiptById(receiptId);
        final ReceiptResult result = loadReceiptResultByIdAndCheckReceipt(resultId, receipt);
        final ReceiptItem item = loadReceiptItemByIdAndCheckResult(itemId, result);
        return ResponseEntity.ok(receiptItemResourceAssembler.toResource(item));
    }

    private ReceiptResult loadReceiptResultByIdAndCheckReceipt(final String resultId, final Receipt receipt) {
        final ReceiptResult result = receiptResultRepository.findOne(resultId);
        if (result == null) {
            log.warn("ILLEGAL RECEIPT RESULT ACCESS! No such receipt result Id: {}.", resultId);
            throw new ResourceNotFoundException("No receipt result with the id: " + resultId);
        }
        if (!receipt.equals(result.getReceipt())) {
            log.warn("ILLEGAL RECEIPT RESULT ACCESS! Result '{}' not belong to Receipt '{}'.", resultId, receipt.getId());
            throw new ResourceNotFoundException("No receipt result with the id: " + resultId); // we treat image not belong to receipt as 404
        }
        return result;
    }

    private ReceiptItem loadReceiptItemByIdAndCheckResult(final String itemId, final ReceiptResult result) {
        final ReceiptItem item = receiptItemRepository.findOne(itemId);
        if (item == null) {
            log.warn("ILLEGAL RECEIPT ITEM ACCESS! No such receipt item Id: {}.", itemId);
            throw new ResourceNotFoundException("No receipt item with the id: " + itemId);
        }
        if (!result.equals(item.getReceiptResult())) {
            log.warn("ILLEGAL RECEIPT ITEM ACCESS! Item '{}' not belong to Result '{}'.", itemId, result.getId());
            throw new ResourceNotFoundException("No receipt item with the id: " + itemId); // we treat item not belong to result as 404
        }
        return item;
    }

}
