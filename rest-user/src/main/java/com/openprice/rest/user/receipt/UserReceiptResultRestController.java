package com.openprice.rest.user.receipt;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openprice.domain.account.user.UserAccountService;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.domain.receipt.ReceiptItemRepository;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.domain.receipt.ReceiptResult;
import com.openprice.domain.receipt.ReceiptResultRepository;
import com.openprice.domain.receipt.ReceiptService;
import com.openprice.domain.receipt.ReceiptUploadService;
import com.openprice.internal.client.InternalService;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.UtilConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * REST API Controller for current user receipt parsed data management.
 *
 */
@RestController
@Slf4j
public class UserReceiptResultRestController extends AbstractUserReceiptRestController {

    private final ReceiptResultRepository receiptResultRepository;
    private final ReceiptItemRepository receiptItemRepository;
    private final UserReceiptResultResource.Assembler receiptResultResourceAssembler;
    private final UserReceiptItemResource.Assembler receiptItemResourceAssembler;

    @Inject
    public UserReceiptResultRestController(final UserAccountService userAccountService,
                                           final ReceiptService receiptService,
                                           final ReceiptUploadService receiptUploadService,
                                           final ReceiptRepository receiptRepository,
                                           final ReceiptResultRepository receiptResultRepository,
                                           final ReceiptItemRepository receiptItemRepository,
                                           final UserReceiptResultResource.Assembler receiptResultResourceAssembler,
                                           final UserReceiptItemResource.Assembler receiptItemResourceAssembler,
                                           final InternalService internalService) {
        super(userAccountService, receiptService, receiptUploadService, receiptRepository, internalService);
        this.receiptResultRepository = receiptResultRepository;
        this.receiptItemRepository = receiptItemRepository;
        this.receiptResultResourceAssembler = receiptResultResourceAssembler;
        this.receiptItemResourceAssembler = receiptItemResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_USER_RECEIPTS_RECEIPT_RESULT)
    public HttpEntity<UserReceiptResultResource> getUserReceiptResult(
            @PathVariable("receiptId") final String receiptId) {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final ReceiptResult result = receiptResultRepository.findFirstByReceiptOrderByCreatedTimeDesc(receipt);
        //receiptService.getLatestReceiptResult(receipt);

        if (result == null) {
            throw new ResourceNotFoundException("Cannot load parser result!");
        }

        return ResponseEntity.ok(receiptResultResourceAssembler.toResource(result));
    }

    /**
     * Return paginated receipt item list, filtered out user deleted items.
     *
     * @param receiptId
     * @param pageable
     * @param assembler
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS)
    public HttpEntity<PagedResources<UserReceiptItemResource>> getUserReceiptItems(
            @PathVariable("receiptId") final String receiptId,
            @PageableDefault(size = UtilConstants.MAX_RETURN_RECORD_COUNT, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<ReceiptItem> assembler) {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final ReceiptResult result = receiptResultRepository.findFirstByReceiptOrderByCreatedTimeDesc(receipt);
        if (result == null) {
            throw new ResourceNotFoundException("Cannot load parser result!");
        }

        final Page<ReceiptItem> items = receiptItemRepository.findByReceiptResultAndIgnoredIsFalseOrderByLineNumber(result, pageable);
        return ResponseEntity.ok(assembler.toResource(items, receiptItemResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS_ITEM)
    public HttpEntity<UserReceiptItemResource> getUserReceiptItemById(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("itemId") final String itemId) throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final ReceiptItem item = getReceiptItemByIdAndCheckReceipt(itemId, receipt);
        return ResponseEntity.ok(receiptItemResourceAssembler.toResource(item));
    }

    @RequestMapping(method = RequestMethod.PUT, value = URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS_ITEM)
    public HttpEntity<Void> updateUserReceiptItem(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("itemId") final String itemId,
            @RequestBody final UserReceiptItemForm form) throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final ReceiptItem item = getReceiptItemByIdAndCheckReceipt(itemId, receipt);
        receiptItemRepository.save(form.updateReceiptItem(item));
        return ResponseEntity.noContent().build();
    }

    /**
     * Set item ignore property to true, so user cannot see it.
     * @param receiptId
     * @param itemId
     * @return
     * @throws ResourceNotFoundException
     */
    @RequestMapping(method = RequestMethod.DELETE, value = URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS_ITEM)
    public HttpEntity<Void> deleteReceiptItemById(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("itemId") final String itemId) throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final ReceiptItem item = getReceiptItemByIdAndCheckReceipt(itemId, receipt);
        item.setIgnored(true);
        receiptItemRepository.save(item);
        return ResponseEntity.noContent().build();
    }

    private ReceiptItem getReceiptItemByIdAndCheckReceipt(final String itemId, final Receipt receipt) {
        final ReceiptItem item = receiptItemRepository.findOne(itemId);
        if (item == null) {
            log.warn("ILLEGAL RECEIPT ITEM ACCESS! No such receipt item Id: {}.", itemId);
            throw new ResourceNotFoundException("No receipt item with the id: " + itemId);
        }
        if (item.getIgnored()) {
            log.warn("ILLEGAL RECEIPT ITEM ACCESS! Receipt item Id: {} was ignored by user.", itemId);
            throw new ResourceNotFoundException("No receipt item with the id: " + itemId);
        }
        if (!receipt.equals(item.getReceiptResult().getReceipt())) {
            log.warn("ILLEGAL RECEIPT ITEM ACCESS! Item '{}' not belong to Receipt '{}'.", itemId, receipt.getId());
            throw new ResourceNotFoundException("No receipt item with the id: " + itemId); // we treat item not belong to receipt as 404
        }
        return item;
    }

}
