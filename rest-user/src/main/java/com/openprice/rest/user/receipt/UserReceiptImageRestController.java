package com.openprice.rest.user.receipt;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.ByteStreams;
import com.openprice.domain.account.user.UserAccountService;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.domain.receipt.ReceiptService;
import com.openprice.domain.receipt.ReceiptUploadService;
import com.openprice.internal.client.InternalService;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.UtilConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * REST API Controller for current user receipt image management.
 *
 */
@RestController
@Slf4j
public class UserReceiptImageRestController extends AbstractUserReceiptRestController {

    private final ReceiptImageRepository receiptImageRepository;
    private final UserReceiptImageResourceAssembler receiptImageResourceAssembler;

    @Inject
    public UserReceiptImageRestController(final UserAccountService userAccountService,
                                          final ReceiptService receiptService,
                                          final ReceiptUploadService receiptUploadService,
                                          final ReceiptRepository receiptRepository,
                                          final ReceiptImageRepository receiptImageRepository,
                                          final UserReceiptImageResourceAssembler receiptImageResourceAssembler,
                                          final InternalService internalService) {
        super(userAccountService, receiptService, receiptUploadService, receiptRepository, internalService);
        this.receiptImageRepository = receiptImageRepository;
        this.receiptImageResourceAssembler = receiptImageResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_USER_RECEIPTS_RECEIPT_IMAGES)
    public HttpEntity<PagedResources<UserReceiptImageResource>> getUserReceiptImages(
            @PathVariable("receiptId") final String receiptId,
            @PageableDefault(size = UtilConstants.MAX_RETURN_RECORD_COUNT, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<ReceiptImage> assembler) {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final Page<ReceiptImage> images = receiptImageRepository.findByReceiptOrderByCreatedTime(receipt, pageable);
        return ResponseEntity.ok(assembler.toResource(images, receiptImageResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE)
    public HttpEntity<UserReceiptImageResource> getUserReceiptImageById(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("imageId") final String imageId)
                    throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final ReceiptImage image = getReceiptImageByIdAndCheckReceipt(imageId, receipt);
        return ResponseEntity.ok(receiptImageResourceAssembler.toResource(image));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = URL_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE)
    public HttpEntity<Void> deleteReceiptImageById(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("imageId") final String imageId)
                    throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final ReceiptImage image = getReceiptImageByIdAndCheckReceipt(imageId, receipt);
        receiptImageRepository.delete(image);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = URL_USER_RECEIPTS_RECEIPT_IMAGES)
    public HttpEntity<Void> createReceiptImageWithBase64String(@PathVariable("receiptId") final String receiptId,
                                                               @RequestBody final ImageDataForm imageDataForm) {
        final ReceiptImage image = newReceiptImageWithBase64ImageData(receiptId, imageDataForm.getBase64String());
        addReceiptImageToProcessQueue(image);

        final URI location =
            linkTo(methodOn(UserReceiptImageRestController.class).getUserReceiptImageById(receiptId, image.getId())).toUri();
        return ResponseEntity.created(location).body(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_USER_RECEIPTS_RECEIPT_IMAGES_UPLOAD)
    public HttpEntity<Void> getUploadReceiptImagePath(@PathVariable("receiptId") final String receiptId) {
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = URL_USER_RECEIPTS_RECEIPT_IMAGES_UPLOAD)
    public HttpEntity<Void> uploadReceiptImage(
            @PathVariable("receiptId") final String receiptId,
            @RequestParam(value="file") final MultipartFile file) {
        if (!file.isEmpty()) {
            final ReceiptImage image = newReceiptImageWithFile(receiptId, file);
            addReceiptImageToProcessQueue(image);
            final URI location =
                linkTo(methodOn(UserReceiptImageRestController.class).getUserReceiptImageById(receiptId, image.getId())).toUri();
            return ResponseEntity.created(location).body(null);
        } else {
            log.error("No file uploaded!");
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE_DOWNLOAD,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> downloadUserReceiptImage(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("imageId") final String imageId) throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final ReceiptImage image = getReceiptImageByIdAndCheckReceipt(imageId, receipt);
        return ResponseEntity.ok(new PathResource(receiptUploadService.getImageFile(image)));
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE_BASE64,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> downloadUserReceiptImageAsBase64(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("imageId") final String imageId) throws ResourceNotFoundException {
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        final ReceiptImage image = getReceiptImageByIdAndCheckReceipt(imageId, receipt);
        final Resource resource = new PathResource(receiptUploadService.getImageFile(image));

        try {
            final byte[] content = ByteStreams.toByteArray(resource.getInputStream());
            final String base64String = new String(Base64.getEncoder().encode(content));
            return ResponseEntity.ok(base64String);
        } catch (IOException ex) {
            log.error("Cannot load image file from {}, please check file system!", image.getFileName());
            throw new ResourceNotFoundException("No image with the id: " + imageId);
        }
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
}
