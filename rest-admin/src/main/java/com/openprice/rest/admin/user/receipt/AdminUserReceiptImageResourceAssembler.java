package com.openprice.rest.admin.user.receipt;


import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.admin.AdminApiUrls;

@Component
public class AdminUserReceiptImageResourceAssembler
    implements ResourceAssembler<ReceiptImage, AdminUserReceiptImageResource>, AdminApiUrls {

    public static final String LINK_NAME_RECEIPT = "receipt";
    public static final String LINK_NAME_DOWNLOAD = "download";
    public static final String LINK_NAME_BASE64 = "base64";

    @Override
    public AdminUserReceiptImageResource toResource(final ReceiptImage receiptImage) {
        final String[] pairs = {"userId", receiptImage.getReceipt().getUser().getId(),
                                "receiptId", receiptImage.getReceipt().getId(),
                                "imageId", receiptImage.getId()};
        final AdminUserReceiptImageResource resource = new AdminUserReceiptImageResource(receiptImage);
        final LinkBuilder linkBuilder = new LinkBuilder(resource);
        linkBuilder.addLink(Link.REL_SELF, URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE, false, pairs)
                   .addLink(LINK_NAME_RECEIPT, URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT, false, pairs)
                   .addLink(LINK_NAME_DOWNLOAD, URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE_DOWNLOAD, false, pairs)
                   .addLink(LINK_NAME_BASE64, URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE_BASE64, false, pairs)
                   ;
        return resource;
    }

}
