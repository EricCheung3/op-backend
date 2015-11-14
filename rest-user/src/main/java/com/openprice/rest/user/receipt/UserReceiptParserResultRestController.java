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
import com.openprice.domain.receipt.ParserResult;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.domain.receipt.ReceiptItemRepository;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.domain.receipt.ReceiptService;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.user.UserApiUrls;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserReceiptParserResultRestController extends AbstractUserReceiptRestController {
    private final ReceiptItemRepository receiptItemRepository;
    private final UserReceiptParserResultResourceAssembler parserResultResourceAssembler;
    private final UserReceiptItemResourceAssembler receiptItemResourceAssembler;

    @Inject
    public UserReceiptParserResultRestController(final UserAccountService userAccountService,
                                                 final ReceiptRepository receiptRepository,
                                                 final ReceiptItemRepository receiptItemRepository,
                                                 final ReceiptService receiptService,
                                                 final UserReceiptParserResultResourceAssembler parserResultResourceAssembler,
                                                 final UserReceiptItemResourceAssembler receiptItemResourceAssembler) {
        super(userAccountService, receiptRepository, receiptService);
        this.receiptItemRepository = receiptItemRepository;
        this.parserResultResourceAssembler = parserResultResourceAssembler;
        this.receiptItemResourceAssembler = receiptItemResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_RECEIPTS_RECEIPT_RESULT)
    public HttpEntity<UserReceiptParserResultResource> getUserReceiptParserResult(
            @PathVariable("receiptId") final String receiptId) {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final ParserResult result = receiptService.getLatestReceiptParserResult(receipt);
        return ResponseEntity.ok(parserResultResourceAssembler.toResource(result));
    }

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS)
    public HttpEntity<PagedResources<UserReceiptItemResource>> getUserReceiptParserResultItems(
            @PathVariable("receiptId") final String receiptId,
            @PageableDefault(size = UtilConstants.MAX_RETURN_RECORD_COUNT, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<ReceiptItem> assembler) {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final ParserResult result = receiptService.getLatestReceiptParserResult(receipt);
        final Page<ReceiptItem> items = receiptItemRepository.findByResult(result, pageable);
        return ResponseEntity.ok(assembler.toResource(items, receiptItemResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS_ITEM)
    public HttpEntity<UserReceiptItemResource> getUserReceiptParserResultItemById(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("itemId") final String itemId)
                    throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final ReceiptItem item = getReceiptItemByIdAndCheckReceipt(itemId, receipt);
        return ResponseEntity.ok(receiptItemResourceAssembler.toResource(item));
    }

    @RequestMapping(method = RequestMethod.PUT, value = UserApiUrls.URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS_ITEM)
    public HttpEntity<Void> updateUserReceiptParserResultItem(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("itemId") final String itemId,
            @RequestBody final UserReceiptItemForm form) throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final ReceiptItem item = getReceiptItemByIdAndCheckReceipt(itemId, receipt);
        receiptItemRepository.save(form.updateReceiptItem(item));
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = UserApiUrls.URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS_ITEM)
    public HttpEntity<Void> deleteReceiptImageById(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("itemId") final String itemId)
                    throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final ReceiptItem item = getReceiptItemByIdAndCheckReceipt(itemId, receipt);
        receiptItemRepository.delete(item); // FIXME set ignore item to TRUE
        return ResponseEntity.noContent().build();
    }

    private ReceiptItem getReceiptItemByIdAndCheckReceipt(final String itemId, final Receipt receipt) {
        final ReceiptItem item = receiptItemRepository.findOne(itemId);
        if (item == null) {
            log.warn("ILLEGAL RECEIPT ITEM ACCESS! No such receipt item Id: {}.", itemId);
            throw new ResourceNotFoundException("No receipt item with the id: " + itemId);
        }
        if (!receipt.equals(item.getResult().getReceipt())) {
            log.warn("ILLEGAL RECEIPT ITEM ACCESS! Item '{}' not belong to Receipt '{}'.", itemId, receipt.getId());
            throw new ResourceNotFoundException("No receipt item with the id: " + itemId); // we treat item not belong to receipt as 404
        }
        return item;
    }

}
