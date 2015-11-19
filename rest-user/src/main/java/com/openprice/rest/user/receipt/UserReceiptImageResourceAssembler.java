package com.openprice.rest.user.receipt;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;

@Component
public class UserReceiptImageResourceAssembler implements ResourceAssembler<ReceiptImage, UserReceiptImageResource>, UserApiUrls {

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
        return resource;
    }

}
