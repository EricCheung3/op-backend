package com.openprice.rest.user.receipt;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserAccountService;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.domain.receipt.ReceiptService;
import com.openprice.process.ProcessQueueService;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.user.ImageDataForm;
import com.openprice.rest.user.UserApiUrls;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserReceiptRestController extends AbstractUserReceiptRestController {
    private final UserReceiptResourceAssembler receiptResourceAssembler;
    private final ProcessQueueService processQueueService;

    @Inject
    public UserReceiptRestController(final UserAccountService userAccountService,
                                     final ReceiptRepository receiptRepository,
                                     final ReceiptImageRepository receiptImageRepository,
                                     final ReceiptService receiptService,
                                     final UserReceiptResourceAssembler receiptResourceAssembler,
                                     final UserReceiptImageResourceAssembler receiptImageResourceAssembler,
                                     final ProcessQueueService processQueueService) {
        super(userAccountService, receiptRepository, receiptService);
        this.receiptResourceAssembler = receiptResourceAssembler;
        this.processQueueService = processQueueService;
    }

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_RECEIPTS)
    public HttpEntity<PagedResources<UserReceiptResource>> getCurrentUserReceipts(
            @PageableDefault(size = 3, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<Receipt> assembler) {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        final Page<Receipt> receipts =
                receiptRepository.findByUserOrderByCreatedTimeDesc(currentUser, pageable);
        return ResponseEntity.ok(assembler.toResource(receipts, receiptResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_RECEIPTS_RECEIPT)
    public HttpEntity<UserReceiptResource> getUserReceiptById(@PathVariable("receiptId") final String receiptId)
            throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        return ResponseEntity.ok(receiptResourceAssembler.toResource(receipt));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = UserApiUrls.URL_USER_RECEIPTS_RECEIPT)
    public HttpEntity<Void> deleteReceiptById(@PathVariable("receiptId") final String receiptId)
            throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        receiptRepository.delete(receipt);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = UserApiUrls.URL_USER_RECEIPTS)
    public HttpEntity<Void> createNewReceiptWithBase64String(@RequestBody final ImageDataForm imageDataForm) {
        final Receipt receipt = newReceiptWithBase64ImageData(imageDataForm.getBase64String());
        processQueueService.addImage(receipt.getImages().get(0));

        URI location = linkTo(methodOn(UserReceiptRestController.class).getUserReceiptById(receipt.getId())).toUri();
        return ResponseEntity.created(location).body(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_RECEIPTS_UPLOAD)
    public HttpEntity<Void> getUploadNewReceiptPath() {
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = UserApiUrls.URL_USER_RECEIPTS_UPLOAD)
    public HttpEntity<Void> uploadNewReceipt(@RequestParam("file") final MultipartFile file) {

        if (!file.isEmpty()) {
            final Receipt receipt = newReceiptWithFile(file);
            processQueueService.addImage(receipt.getImages().get(0));

            URI location = linkTo(methodOn(UserReceiptRestController.class).getUserReceiptById(receipt.getId())).toUri();
            return ResponseEntity.created(location).body(null);
        }
        else {
            log.error("No file uploaded!");
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_RECEIPTS_RECEIPT_ITEMS)
    public HttpEntity<List<ReceiptItem>> getUserReceiptItems(
            @PathVariable("receiptId") final String receiptId) {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        List<ReceiptItem> result = receiptService.getParsedReceiptItems(receipt);
        return ResponseEntity.ok(result);
    }

    // TODO add comment feedback
    @RequestMapping(method = RequestMethod.PUT, value = UserApiUrls.URL_USER_RECEIPTS_RECEIPT_FEEDBACK)
    public HttpEntity<Void> setReceiptFeedbackById(
            @PathVariable("receiptId") final String receiptId,
            @RequestBody final Map<String, Integer> updateMap)
                    throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final Integer rating = updateMap.get("rating");
        if (rating != null) {
            setReceiptRating(receipt, rating);
        }
        return ResponseEntity.noContent().build();
    }

    @Transactional
    private void setReceiptRating(Receipt receipt, Integer rating) {
        receipt.setRating(rating);
        receiptRepository.save(receipt);
    }

}
