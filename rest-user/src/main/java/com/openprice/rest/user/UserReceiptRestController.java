package com.openprice.rest.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.ByteStreams;
import com.openprice.domain.account.UserAccount;
import com.openprice.domain.account.UserAccountService;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.domain.receipt.ReceiptService;
import com.openprice.process.ProcessQueueService;
import com.openprice.rest.AbstractRestController;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.common.ImageDataForm;
import com.openprice.rest.common.UserReceiptImageResource;
import com.openprice.rest.common.UserReceiptResource;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserReceiptRestController extends AbstractRestController {
    private final ReceiptRepository receiptRepository;
    private final ReceiptImageRepository receiptImageRepository;
    private final ReceiptService receiptService;
    private final UserReceiptResourceAssembler receiptResourceAssembler;
    private final UserReceiptImageResourceAssembler receiptImageResourceAssembler;
    private final ProcessQueueService processQueueService;
    
    @Inject
    public UserReceiptRestController(final UserAccountService userAccountService,
                                     final ReceiptRepository receiptRepository,
                                     final ReceiptImageRepository receiptImageRepository,
                                     final ReceiptService receiptService,
                                     final UserReceiptResourceAssembler receiptResourceAssembler,
                                     final UserReceiptImageResourceAssembler receiptImageResourceAssembler,
                                     final ProcessQueueService processQueueService) {
        super(userAccountService);
        this.receiptRepository = receiptRepository;
        this.receiptImageRepository = receiptImageRepository;
        this.receiptService = receiptService;
        this.receiptResourceAssembler = receiptResourceAssembler;
        this.receiptImageResourceAssembler = receiptImageResourceAssembler;
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
    
    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_RECEIPTS_RECEIPT_IMAGES)
    public HttpEntity<PagedResources<UserReceiptImageResource>> getUserReceiptImages(
            @PathVariable("receiptId") final String receiptId,
            @PageableDefault(size = UtilConstants.MAX_RETURN_RECORD_COUNT, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<ReceiptImage> assembler) {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final Page<ReceiptImage> images = receiptImageRepository.findByReceiptOrderByCreatedTime(receipt, pageable);
        return ResponseEntity.ok(assembler.toResource(images, receiptImageResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE)
    public HttpEntity<UserReceiptImageResource> getUserReceiptImageById(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("imageId") final String imageId)
                    throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final ReceiptImage image = getReceiptImageByIdAndCheckReceipt(imageId, receipt);
        
        return ResponseEntity.ok(receiptImageResourceAssembler.toResource(image));
        
    }
    
    @RequestMapping(method = RequestMethod.POST, value = UserApiUrls.URL_USER_RECEIPTS_RECEIPT_IMAGES)
    public HttpEntity<Void> createReceiptImageWithBase64String(@PathVariable("receiptId") final String receiptId,
                                                               @RequestBody final ImageDataForm imageDataForm) {
        final ReceiptImage image = newReceiptImageWithBase64ImageData(receiptId, imageDataForm.getBase64String());
        processQueueService.addImage(image);
        
        URI location = linkTo(methodOn(UserReceiptRestController.class).getUserReceiptImageById(receiptId, image.getId())).toUri();
        return ResponseEntity.created(location).body(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_RECEIPTS_RECEIPT_IMAGES_UPLOAD)
    public HttpEntity<Void> getUploadReceiptImagePath(@PathVariable("receiptId") final String receiptId) {
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = UserApiUrls.URL_USER_RECEIPTS_RECEIPT_IMAGES_UPLOAD)
    public HttpEntity<Void> uploadReceiptImage(@PathVariable("receiptId") final String receiptId,
                                               @RequestParam(value="file") final MultipartFile file) {
        if (!file.isEmpty()) {
            
            final ReceiptImage image = newReceiptImageWithFile(receiptId, file);
            processQueueService.addImage(image);
            
            URI location = linkTo(methodOn(UserReceiptRestController.class).getUserReceiptImageById(receiptId, image.getId())).toUri();
            return ResponseEntity.created(location).body(null);
        }
        else {
            log.error("No file uploaded!");
            return ResponseEntity.badRequest().build();
        }
    }
    

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE_DOWNLOAD, 
            produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadUserReceiptImage(@PathVariable("receiptId") final String receiptId,
                                                 @PathVariable("imageId") final String imageId)
                    throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final ReceiptImage image = getReceiptImageByIdAndCheckReceipt(imageId, receipt);
        
        Resource resource = new PathResource(receiptService.getImageFile(image));
        return ResponseEntity.ok(resource);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE_BASE64, 
            produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> downloadUserReceiptImageAsBase64(@PathVariable("receiptId") final String receiptId,
                                                 @PathVariable("imageId") final String imageId)
                    throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final ReceiptImage image = getReceiptImageByIdAndCheckReceipt(imageId, receipt);
        
        final Resource resource = new PathResource(receiptService.getImageFile(image));
        
        try {
            final byte[] content = ByteStreams.toByteArray(resource.getInputStream());
            final String base64String = new String(Base64.getEncoder().encode(content));
            return ResponseEntity.ok(base64String);
        } catch (IOException ex) {
            log.error("Cannot load image file from {}, please check file system!", image.getFileName());
            throw new ResourceNotFoundException("No image with the id: " + imageId);
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_RECEIPTS_RECEIPT_ITEMS)
    public HttpEntity<List<ReceiptItem>> getUserReceiptItems(
            @PathVariable("receiptId") final String receiptId) {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        List<ReceiptItem> result = receiptService.getParsedReceiptItems(receipt);
        return ResponseEntity.ok(result);
    }

    private Receipt getReceiptByIdAndCheckUser(final String receiptId) 
            throws ResourceNotFoundException, AccessDeniedException {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
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
    private Receipt newReceiptWithBase64ImageData(final String base64Data) {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        log.debug("User {} upload image as base64 string for new receipt", currentUser.getUsername());
        return receiptService.uploadImageForNewReceipt(currentUser, base64Data);
    }
    
    @Transactional
    private Receipt newReceiptWithFile(final MultipartFile file) {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        log.debug("User {} upload image file for new receipt", currentUser.getUsername());
        return receiptService.uploadImageForNewReceipt(currentUser, file);
    }
    
    @Transactional
    private ReceiptImage newReceiptImageWithBase64ImageData(final String receiptId, final String base64Data) {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        log.debug("User {} upload image base64 string for receipt {}.", currentUser.getUsername(), receiptId);
        return receiptService.appendImageToReceipt(receipt, base64Data);
    }
    
    @Transactional
    private ReceiptImage newReceiptImageWithFile(final String receiptId, final MultipartFile file) {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        log.debug("User {} upload image file for receipt {}.", currentUser.getUsername(), receiptId);
        return receiptService.appendImageToReceipt(receipt, file);
    }
}
