package com.openprice.rest.user.receipt;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openprice.domain.account.user.UserAccountService;
import com.openprice.domain.receipt.ParserResult;
import com.openprice.domain.receipt.ParserResultRepository;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptItemRepository;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.domain.receipt.ReceiptService;
import com.openprice.rest.user.UserApiUrls;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserReceiptParserResultRestController extends AbstractUserReceiptRestController {
    private final ParserResultRepository parserResultRepository;
    private final ReceiptItemRepository receiptItemRepository;
    private final UserReceiptParserResultResourceAssembler parserResultResourceAssembler;
    private final UserReceiptItemResourceAssembler receiptItemResourceAssembler;

    @Inject
    public UserReceiptParserResultRestController(final UserAccountService userAccountService,
                                                 final ReceiptRepository receiptRepository,
                                                 final ParserResultRepository parserResultRepository,
                                                 final ReceiptItemRepository receiptItemRepository,
                                                 final ReceiptService receiptService,
                                                 final UserReceiptParserResultResourceAssembler parserResultResourceAssembler,
                                                 final UserReceiptItemResourceAssembler receiptItemResourceAssembler) {
        super(userAccountService, receiptRepository, receiptService);
        this.parserResultRepository = parserResultRepository;
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

    //TODO update receipt item
}
