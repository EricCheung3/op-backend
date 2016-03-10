package com.openprice.rest.user.receipt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.inject.Inject;

import org.springframework.core.io.PathResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.common.io.ByteStreams;
import com.openprice.common.ImageResourceUtils;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptUploadService;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

public class UserReceiptImageResource extends Resource<ReceiptImage> {

    @Getter
    private String downloadUrl;

    @Getter
    private String base64Url; // TODO remove it after UI updates

    @Getter
    private String base64;

    public UserReceiptImageResource(final ReceiptImage resource) {
        super(resource);
    }

    @Component
    @Slf4j
    public static class Assembler implements ResourceAssembler<ReceiptImage, UserReceiptImageResource>, UserApiUrls {

        private final ReceiptUploadService receiptUploadService; // TODO remove it later

        @Inject
        public Assembler(final ReceiptUploadService receiptUploadService) {
            this.receiptUploadService = receiptUploadService;
        }

        @Override
        public UserReceiptImageResource toResource(final ReceiptImage receiptImage) {
            final String[] pairs = {"receiptId", receiptImage.getReceipt().getId(), "imageId", receiptImage.getId()};
            final UserReceiptImageResource resource = new UserReceiptImageResource(receiptImage);
            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE, false, pairs)
                       .addLink("receipt", URL_USER_RECEIPTS_RECEIPT, false, pairs)
                       .addLink("download", URL_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE_DOWNLOAD, false, pairs)
                       .addLink("base64", URL_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE_BASE64, false, pairs)
                       ;
            resource.downloadUrl = resource.getLink("download").getHref();
            resource.base64Url = resource.getLink("base64").getHref();
            resource.base64 = receiptImage.getBase64();

            if (StringUtils.isEmpty(receiptImage.getBase64())) {
                log.info("No base64 for image, load dynamically...");
                final PathResource imageFileResource = new PathResource(receiptUploadService.getImageFile(receiptImage));
                try (final InputStream is = imageFileResource.getInputStream()){
                    final byte[] content = ByteStreams.toByteArray(is);
                    final byte[] resizedContent = ImageResourceUtils.resizeJpgImage(content);
                    resource.base64 = new String(Base64.getEncoder().encode(resizedContent));
                    log.info("Original image size {}K, after resize become {}K.", content.length/1000, resizedContent.length/1000);
                } catch (IOException ex) {
                    // ignore
                }
            }

            return resource;
        }

    }
}
