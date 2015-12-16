package com.openprice.rest.user.receipt;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;

import lombok.Getter;

public class UserReceiptImageResource extends Resource<ReceiptImage> {

    @Getter
    private String downloadUrl;

    @Getter
    private String base64Url;

    public UserReceiptImageResource(final ReceiptImage resource) {
        super(resource);
    }

    @Component
    public static class Assembler implements ResourceAssembler<ReceiptImage, UserReceiptImageResource>, UserApiUrls {

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

            return resource;
        }

    }
}
