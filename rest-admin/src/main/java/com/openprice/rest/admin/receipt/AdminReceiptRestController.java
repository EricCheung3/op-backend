package com.openprice.rest.admin.receipt;

import java.io.IOException;
import java.util.Base64;

import javax.inject.Inject;

import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.io.ByteStreams;
import com.openprice.domain.account.admin.AdminAccountService;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.domain.receipt.ReceiptService;
import com.openprice.domain.receipt.ReceiptUploadService;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.UtilConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * REST API Controller for global receipt management by admin.
 *
 */
@RestController
@Slf4j
public class AdminReceiptRestController extends AbstractReceiptAdminRestController {

    private final ReceiptImageRepository receiptImageRepository;
    private final AdminReceiptResource.Assembler receiptResourceAssembler;
    private final AdminReceiptImageResource.Assembler receiptImageResourceAssembler;

    @Inject
    public AdminReceiptRestController(final AdminAccountService adminAccountService,
                                      final ReceiptService receiptService,
                                      final ReceiptUploadService receiptUploadService,
                                      final ReceiptRepository receiptRepository,
                                      final ReceiptImageRepository receiptImageRepository,
                                      final AdminReceiptResource.Assembler receiptResourceAssembler,
                                      final AdminReceiptImageResource.Assembler receiptImageResourceAssembler) {
        super(adminAccountService, receiptService, receiptUploadService, receiptRepository);
        this.receiptImageRepository = receiptImageRepository;
        this.receiptResourceAssembler = receiptResourceAssembler;
        this.receiptImageResourceAssembler = receiptImageResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_ADMIN_RECEIPTS)
    public HttpEntity<PagedResources<AdminReceiptResource>> getAllReceipts(
            @PageableDefault(size = UtilConstants.DEFAULT_RETURN_RECORD_COUNT, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<Receipt> assembler) {
        final Page<Receipt> receipts = receiptRepository.findAll(
                new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Direction.DESC, "createdTime"));
        return ResponseEntity.ok(assembler.toResource(receipts, receiptResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_ADMIN_RECEIPTS_RECEIPT)
    public HttpEntity<AdminReceiptResource> getReceiptById(
            @PathVariable("receiptId") final String receiptId) throws ResourceNotFoundException {
        final Receipt receipt = loadReceiptById(receiptId);
        return ResponseEntity.ok(receiptResourceAssembler.toResource(receipt));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = URL_ADMIN_RECEIPTS_RECEIPT)
    public HttpEntity<Void> deleteReceiptById(
            @PathVariable("receiptId") final String receiptId) throws ResourceNotFoundException {
        final Receipt receipt = loadReceiptById(receiptId);
        receiptRepository.delete(receipt);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_ADMIN_RECEIPTS_RECEIPT_IMAGES)
    public HttpEntity<PagedResources<AdminReceiptImageResource>> getReceiptImages(
            @PathVariable("receiptId") final String receiptId,
            @PageableDefault(size = UtilConstants.MAX_RETURN_RECORD_COUNT, page = 0) final Pageable pageable,
            final PagedResourcesAssembler<ReceiptImage> assembler) {
        final Receipt receipt = loadReceiptById(receiptId);
        final Page<ReceiptImage> images = receiptImageRepository.findByReceiptOrderByCreatedTime(receipt, pageable);
        return ResponseEntity.ok(assembler.toResource(images, receiptImageResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_ADMIN_RECEIPTS_RECEIPT_IMAGES_IMAGE)
    public HttpEntity<AdminReceiptImageResource> getReceiptImageById(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("imageId") final String imageId) throws ResourceNotFoundException {
        final Receipt receipt = loadReceiptById(receiptId);
        final ReceiptImage image = loadReceiptImageByIdAndCheckReceipt(imageId, receipt);
        return ResponseEntity.ok(receiptImageResourceAssembler.toResource(image));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = URL_ADMIN_RECEIPTS_RECEIPT_IMAGES_IMAGE)
    public HttpEntity<Void> deleteReceiptImageById(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("imageId") final String imageId) throws ResourceNotFoundException {
        final Receipt receipt = loadReceiptById(receiptId);
        final ReceiptImage image = loadReceiptImageByIdAndCheckReceipt(imageId, receipt);
        receiptImageRepository.delete(image);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_ADMIN_RECEIPTS_RECEIPT_IMAGES_IMAGE_DOWNLOAD,
            produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadReceiptImage(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("imageId") final String imageId) throws ResourceNotFoundException {
        final Receipt receipt = loadReceiptById(receiptId);
        final ReceiptImage image = loadReceiptImageByIdAndCheckReceipt(imageId, receipt);
        return ResponseEntity.ok(new PathResource(receiptUploadService.getImageFile(image)));
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_ADMIN_RECEIPTS_RECEIPT_IMAGES_IMAGE_BASE64,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> downloadUserReceiptImageAsBase64(
            @PathVariable("receiptId") final String receiptId,
            @PathVariable("imageId") final String imageId) throws ResourceNotFoundException {
        final Receipt receipt = loadReceiptById(receiptId);
        final ReceiptImage image = loadReceiptImageByIdAndCheckReceipt(imageId, receipt);
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

    private ReceiptImage loadReceiptImageByIdAndCheckReceipt(final String imageId, final Receipt receipt) {
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
