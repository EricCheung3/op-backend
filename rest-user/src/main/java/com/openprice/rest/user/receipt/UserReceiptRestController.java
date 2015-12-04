package com.openprice.rest.user.receipt;

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
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.domain.receipt.ReceiptService;
import com.openprice.domain.receipt.ReceiptUploadService;
import com.openprice.internal.client.InternalService;
import com.openprice.rest.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserReceiptRestController extends AbstractUserReceiptRestController {

    private final UserReceiptResource.Assembler receiptResourceAssembler;

    @Inject
    public UserReceiptRestController(final UserAccountService userAccountService,
                                     final ReceiptService receiptService,
                                     final ReceiptUploadService receiptUploadService,
                                     final ReceiptRepository receiptRepository,
                                     final UserReceiptResource.Assembler receiptResourceAssembler,
                                     final InternalService internalService) {
        super(userAccountService, receiptService, receiptUploadService, receiptRepository, internalService);
        this.receiptResourceAssembler = receiptResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_USER_RECEIPTS)
    public HttpEntity<PagedResources<UserReceiptResource>> getCurrentUserReceipts(
            @PageableDefault(size = 3, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<Receipt> assembler) {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        final Page<Receipt> receipts = receiptRepository.findByUserOrderByCreatedTimeDesc(currentUser, pageable);
        return ResponseEntity.ok(assembler.toResource(receipts, receiptResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_USER_RECEIPTS_RECEIPT)
    public HttpEntity<UserReceiptResource> getUserReceiptById(@PathVariable("receiptId") final String receiptId)
            throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        return ResponseEntity.ok(receiptResourceAssembler.toResource(receipt));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = URL_USER_RECEIPTS_RECEIPT)
    public HttpEntity<Void> deleteReceiptById(@PathVariable("receiptId") final String receiptId)
            throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        receiptRepository.delete(receipt);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = URL_USER_RECEIPTS)
    public HttpEntity<Void> createNewReceiptWithBase64String(@RequestBody final ImageDataForm imageDataForm) {
        final Receipt receipt = newReceiptWithBase64ImageData(imageDataForm.getBase64String());
        addReceiptImageToProcessQueue(receipt.getImages().get(0));
        final URI location = linkTo(methodOn(UserReceiptRestController.class).getUserReceiptById(receipt.getId())).toUri();
        return ResponseEntity.created(location).body(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_USER_RECEIPTS_UPLOAD)
    public HttpEntity<Void> getUploadNewReceiptPath() {
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = URL_USER_RECEIPTS_UPLOAD)
    public HttpEntity<String> uploadNewReceipt(@RequestParam("file") final MultipartFile file) {
        if (!file.isEmpty()) {
            final Receipt receipt = newReceiptWithFile(file);
            addReceiptImageToProcessQueue(receipt.getImages().get(0));
            final URI location = linkTo(methodOn(UserReceiptRestController.class).getUserReceiptById(receipt.getId())).toUri();
            return ResponseEntity.created(location).body(receipt.getId());
        }
        else {
            log.error("No file uploaded!");
            return ResponseEntity.badRequest().body("");
        }
    }

    /**
     * Temp solution to upload receipt image with OCR result text.
     * @param file
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = URL_USER_RECEIPTS_HACKLOAD)
    public HttpEntity<Void> hackloadNewReceiptWithOcrResult(
            @RequestParam("image") final MultipartFile image,
            @RequestParam("ocr") final MultipartFile ocr) {
        if (!image.isEmpty() && !ocr.isEmpty()) {
            final Receipt receipt = newReceiptWithImageAndOcrFile(image, ocr);
            final URI location = linkTo(methodOn(UserReceiptRestController.class).getUserReceiptById(receipt.getId())).toUri();
            return ResponseEntity.created(location).body(null);
        }
        else {
            log.error("No file uploaded!");
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = URL_USER_RECEIPTS_RECEIPT_HACKLOAD_OCR)
    public HttpEntity<Void> hackloadOcrResult(
            @PathVariable("receiptId") final String receiptId,
            @RequestParam("ocr") final MultipartFile ocr) {
        if (!ocr.isEmpty()) {
            setOcrResultOfReceipt(receiptId, ocr);
            final URI location = linkTo(methodOn(UserReceiptRestController.class).getUserReceiptById(receiptId)).toUri();
            return ResponseEntity.created(location).body(null);
        }
        else {
            log.error("No file uploaded!");
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = URL_USER_RECEIPTS_RECEIPT_FEEDBACK)
    public HttpEntity<Void> addReceiptFeedback(
            @PathVariable("receiptId") final String receiptId,
            @RequestBody final FeedbackForm form) throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        receiptService.addFeedback(receipt, form.getRating(), form.getComment());
        return ResponseEntity.noContent().build();
    }

    @Transactional
    protected Receipt newReceiptWithImageAndOcrFile(final MultipartFile image, final MultipartFile ocr) {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        log.debug("User {} HACKLoad image file for new receipt and OCR result.", currentUser.getUsername());
        return receiptUploadService.hackloadImageFileAndOcrResultForNewReceipt(currentUser, image, ocr);
    }

    @Transactional
    private void setOcrResultOfReceipt(final String receiptId, final MultipartFile ocr) {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        receiptUploadService.hackloadOcrResult(receipt, ocr);
    }


}
