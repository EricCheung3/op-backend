package com.openprice.rest.admin.receipt;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.admin.AdminApiUrls;

import lombok.Getter;

public class AdminReceiptImageResource extends Resource<ReceiptImage> {

    @Getter
    private String ocrResult;

    @Getter
    private String downloadUrl;

    @Getter
    private String base64Url;

    public AdminReceiptImageResource(final ReceiptImage resource) {
        super(resource);
    }

    @Component
    public static class Assembler implements ResourceAssembler<ReceiptImage, AdminReceiptImageResource>, AdminApiUrls {

        @Override
        public AdminReceiptImageResource toResource(final ReceiptImage receiptImage) {
            final AdminReceiptImageResource resource = new AdminReceiptImageResource(receiptImage);

            final String[] pairs = {"receiptId", receiptImage.getReceipt().getId(), "imageId", receiptImage.getId()};
            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_ADMIN_RECEIPTS_RECEIPT_IMAGES_IMAGE, false, pairs)
                       .addLink("receipt", URL_ADMIN_RECEIPTS_RECEIPT, false, pairs)
                       .addLink("download", URL_ADMIN_RECEIPTS_RECEIPT_IMAGES_IMAGE_DOWNLOAD, false, pairs)
                       .addLink("base64", URL_ADMIN_RECEIPTS_RECEIPT_IMAGES_IMAGE_BASE64, false, pairs)
                       ;
            resource.ocrResult = receiptImage.getOcrResult();
            resource.downloadUrl = resource.getLink("download").getHref();
            resource.base64Url = resource.getLink("base64").getHref();

            return resource;
        }

    }
}
