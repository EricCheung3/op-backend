package com.openprice.rest.admin.receipt;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;

import javax.inject.Inject;

import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.openprice.domain.account.UserAccount;
import com.openprice.domain.account.UserAccountRepository;
import com.openprice.domain.account.UserAccountService;
import com.openprice.domain.admin.AdminAccountService;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.domain.receipt.ReceiptService;
import com.openprice.process.ProcessQueueService;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.admin.AbstractUserAdminRestController;
import com.openprice.rest.admin.AdminApiUrls;

import lombok.extern.slf4j.Slf4j;

@RestController("admin_UserReceiptRestController")
@Slf4j
public class UserReceiptRestController extends AbstractUserAdminRestController {
    private final ReceiptRepository receiptRepository;
    private final ReceiptImageRepository receiptImageRepository;
    private final ReceiptService receiptService;
    private final UserReceiptResourceAssembler receiptResourceAssembler;
    private final UserReceiptImageResourceAssembler receiptImageResourceAssembler;
    private final ProcessQueueService processQueueService;

    @Inject
    public UserReceiptRestController(final AdminAccountService adminAccountService,
                                     final UserAccountService userAccountService,
                                     final UserAccountRepository userAccountRepository,
                                     final ReceiptRepository receiptRepository,
                                     final ReceiptImageRepository receiptImageRepository,
                                     final ReceiptService receiptService,
                                     final UserReceiptResourceAssembler receiptResourceAssembler,
                                     final UserReceiptImageResourceAssembler receiptImageResourceAssembler,
                                     final ProcessQueueService processQueueService) {
        super(adminAccountService, userAccountService, userAccountRepository);
        this.receiptRepository = receiptRepository;
        this.receiptImageRepository = receiptImageRepository;
        this.receiptService = receiptService;
        this.receiptResourceAssembler = receiptResourceAssembler;
        this.receiptImageResourceAssembler = receiptImageResourceAssembler;
        this.processQueueService = processQueueService;
    }

    @RequestMapping(method = RequestMethod.GET, value = AdminApiUrls.URL_ADMIN_USERS_USER_RECEIPTS)
    public HttpEntity<PagedResources<UserReceiptResource>> getUserReceipts(
            @PathVariable("userId") final String userId,
            @PageableDefault(size = UtilConstants.DEFAULT_RETURN_RECORD_COUNT, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<Receipt> assembler) {
        final UserAccount user = getUserByUserId(userId);
        final Page<Receipt> receipts =
                receiptRepository.findByUserOrderByCreatedTimeDesc(user, pageable);

        return ResponseEntity.ok(assembler.toResource(receipts, receiptResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.GET, value = AdminApiUrls.URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT)
    public HttpEntity<UserReceiptResource> getUserReceiptById(@PathVariable("userId") final String userId,
                                                              @PathVariable("receiptId") final String receiptId)
            throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(userId, receiptId);
        return ResponseEntity.ok(receiptResourceAssembler.toResource(receipt));

    }

    @RequestMapping(method = RequestMethod.GET, value = AdminApiUrls.URL_ADMIN_USERS_USER_RECEIPTS_UPLOAD)
    public HttpEntity<Void> getUploadNewReceiptPath(@PathVariable("userId") final String userId) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, value = AdminApiUrls.URL_ADMIN_USERS_USER_RECEIPTS_UPLOAD)
    public HttpEntity<Void> uploadNewReceipt(@PathVariable("userId") final String userId,
                                             @RequestParam("file") final MultipartFile file) {
        if (!file.isEmpty()) {
            final Receipt receipt = newReceiptWithFile(userId, file);
            processQueueService.addImage(receipt.getImages().get(0));

            URI location = linkTo(methodOn(UserReceiptRestController.class).getUserReceiptById(userId, receipt.getId())).toUri();
            return ResponseEntity.created(location).body(null);
        }
        else {
            log.error("No file uploaded!");
            return ResponseEntity.badRequest().build();
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = AdminApiUrls.URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT_IMAGES)
    public HttpEntity<PagedResources<UserReceiptImageResource>> getUserReceiptImages(
            @PathVariable("userId") final String userId,
            @PathVariable("receiptId") final String receiptId,
            @PageableDefault(size = UtilConstants.MAX_RETURN_RECORD_COUNT, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<ReceiptImage> assembler) {
        final Receipt receipt = getReceiptByIdAndCheckUser(userId, receiptId);
        final Page<ReceiptImage> images = receiptImageRepository.findByReceiptOrderByCreatedTime(receipt, pageable);
        return ResponseEntity.ok(assembler.toResource(images, receiptImageResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.GET, value = AdminApiUrls.URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE)
    public HttpEntity<UserReceiptImageResource> getUserReceiptImageById(
            @PathVariable("userId") final String userId,
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("imageId") final String imageId)
                    throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(userId, receiptId);
        final ReceiptImage image = getReceiptImageByIdAndCheckReceipt(imageId, receipt);

        return ResponseEntity.ok(receiptImageResourceAssembler.toResource(image));

    }

    @RequestMapping(method = RequestMethod.GET, value = AdminApiUrls.URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT_IMAGES_UPLOAD)
    public HttpEntity<Void> getUploadReceiptImagePath(@PathVariable("userId") final String userId,
                                                      @PathVariable("receiptId") final String receiptId) {
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = AdminApiUrls.URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT_IMAGES_UPLOAD)
    public HttpEntity<Void> uploadReceiptImage(@PathVariable("userId") final String userId,
                                               @PathVariable("receiptId") final String receiptId,
                                               @RequestParam(value="file") final MultipartFile file) {
        if (!file.isEmpty()) {
            final ReceiptImage image = newReceiptImageWithFile(userId, receiptId, file);
            processQueueService.addImage(image);

            URI location = linkTo(methodOn(UserReceiptRestController.class).getUserReceiptImageById(userId, receiptId, image.getId())).toUri();
            return ResponseEntity.created(location).body(null);
        }
        else {
            log.error("No file uploaded!");
            return ResponseEntity.badRequest().build();
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = AdminApiUrls.URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE_DOWNLOAD,
            produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadUserReceiptImage(
            @PathVariable("userId") final String userId,
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("imageId") final String imageId)
                    throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(userId, receiptId);
        final ReceiptImage image = getReceiptImageByIdAndCheckReceipt(imageId, receipt);

        Resource resource = new PathResource(receiptService.getImageFile(image));
        return ResponseEntity.ok(resource);
    }


    private Receipt getReceiptByIdAndCheckUser(final String userId, final String receiptId) {
        final UserAccount currentUser = getUserByUserId(userId);
        final Receipt receipt = receiptRepository.findOne(receiptId);
        if (receipt == null) {
            log.warn("ILLEGAL RECEIPT ACCESS! No such receipt Id: {}.", receiptId);
            throw new ResourceNotFoundException("No receipt with the id: " + receiptId);
        }
        if (!currentUser.equals(receipt.getUser())) {
            log.warn("ILLEGAL RECEIPT ACCESS! Receipt '{}' not belong to current user '{}'.", receiptId, currentUser.getId());
            throw new AccessDeniedException("Cannot access the receipt not belong to current user.");
        }
        return receipt;
    }

    private ReceiptImage getReceiptImageByIdAndCheckReceipt(final String imageId, final Receipt receipt) {
        final ReceiptImage image = receiptImageRepository.findOne(imageId);
        if (image == null) {
            log.warn("ILLEGAL RECEIPT IMAGE ACCESS! No such receipt image Id: {}.", imageId);
            throw new ResourceNotFoundException("No receipt image with the id: " + imageId);
        }
        if (!receipt.equals(image.getReceipt())) {
            log.warn("ILLEGAL RECEIPT IMAGE ACCESS! Image '{}' not belong to Receipt '{}'.", imageId, receipt.getId());
            throw new ResourceNotFoundException("No receipt image with the id: " + imageId); // we treat image not belong to receipt as 404
        }
        return image;
    }

    @Transactional
    private Receipt newReceiptWithFile(final String userId, final MultipartFile file) {
        final UserAccount user = getUserByUserId(userId);
        log.debug("Admin upload image for new receipt to user {}", user.getUsername());
        return receiptService.uploadImageForNewReceipt(user, file);
    }

    @Transactional
    private ReceiptImage newReceiptImageWithFile(final String userId,
                                                 final String receiptId,
                                                 final MultipartFile file) {
        final UserAccount user = getUserByUserId(userId);
        final Receipt receipt = getReceiptByIdAndCheckUser(userId, receiptId);
        log.debug("Admin upload image for receipt {} to user {}.", receiptId, user.getUsername());
        return receiptService.appendImageToReceipt(receipt, file);
    }

}