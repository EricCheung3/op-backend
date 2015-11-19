package com.openprice.rest.admin.user.receipt;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.admin.AdminApiUrls;

@Component
public class AdminUserReceiptResourceAssembler implements ResourceAssembler<Receipt, AdminUserReceiptResource>, AdminApiUrls {

    public static final String LINK_NAME_USER = "user";
    public static final String LINK_NAME_IMAGES = "images";
    public static final String LINK_NAME_IMAGE = "image";
    public static final String LINK_NAME_ITEMS = "items";

    private final AdminUserReceiptImageResourceAssembler imageResourceAssembler;

    @Inject
    public AdminUserReceiptResourceAssembler(final AdminUserReceiptImageResourceAssembler imageResourceAssembler) {
        this.imageResourceAssembler = imageResourceAssembler;
    }

    @Override
    public AdminUserReceiptResource toResource(final Receipt receipt) {
        final String[] pairs = {"userId", receipt.getUser().getId(),
                                "receiptId", receipt.getId()};
        final AdminUserReceiptResource resource = new AdminUserReceiptResource(receipt);
        final LinkBuilder linkBuilder = new LinkBuilder(resource);
        linkBuilder.addLink(Link.REL_SELF, URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT, false, pairs)
                   .addLink(LINK_NAME_IMAGES, URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT_IMAGES,  true, pairs)
                   .addLink(LINK_NAME_IMAGE, URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE, false, pairs)
//                   .addLink(LINK_NAME_PARSER_RESULT, URL_USER_RECEIPTS_RECEIPT_RESULT, false, pairs)
//                   .addLink(LINK_NAME_ITEMS, URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS, true, pairs)
//                   .addLink(LINK_NAME_ITEM, URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS_ITEM, false, pairs)
                   .addLink(LINK_NAME_USER, URL_ADMIN_USERS_USER, false, pairs)
                   ;

        // Temp solution for embedded resources
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/270
        List<AdminUserReceiptImageResource> images = new ArrayList<>();
        for (ReceiptImage image : receipt.getImages()) {
            images.add(imageResourceAssembler.toResource(image));
        }
        resource.setImages(images);

        return resource;
    }

}
