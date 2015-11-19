package com.openprice.rest.user.receipt;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;

@Component
public class UserReceiptResourceAssembler implements ResourceAssembler<Receipt, UserReceiptResource>, UserApiUrls {

    public static final String LINK_NAME_IMAGES = "images";
    public static final String LINK_NAME_IMAGE = "image";
    public static final String LINK_NAME_UPLOAD = "upload";
    public static final String LINK_NAME_PARSER_RESULT = "result";
    public static final String LINK_NAME_ITEMS = "items";
    public static final String LINK_NAME_ITEM = "item";
    public static final String LINK_NAME_FEEDBACK = "feedback";

    private final UserReceiptImageResourceAssembler imageResourceAssembler;

    @Inject
    public UserReceiptResourceAssembler(final UserReceiptImageResourceAssembler imageResourceAssembler) {
        this.imageResourceAssembler = imageResourceAssembler;
    }

    @Override
    public UserReceiptResource toResource(final Receipt receipt) {
        final String[] pairs = {"receiptId", receipt.getId()};
        final UserReceiptResource resource = new UserReceiptResource(receipt);
        final LinkBuilder linkBuilder = new LinkBuilder(resource);
        linkBuilder.addLink(Link.REL_SELF, URL_USER_RECEIPTS_RECEIPT, false, pairs)
                   .addLink(LINK_NAME_IMAGES,URL_USER_RECEIPTS_RECEIPT_IMAGES,  true, pairs)
                   .addLink(LINK_NAME_IMAGE, URL_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE, false, pairs)
                   .addLink(LINK_NAME_PARSER_RESULT, URL_USER_RECEIPTS_RECEIPT_RESULT, false, pairs)
                   .addLink(LINK_NAME_ITEMS, URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS, true, pairs)
                   .addLink(LINK_NAME_ITEM, URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS_ITEM, false, pairs)
                   .addLink(LINK_NAME_FEEDBACK, URL_USER_RECEIPTS_RECEIPT_FEEDBACK, false, pairs)
                   .addLink(LINK_NAME_UPLOAD, URL_USER_RECEIPTS_RECEIPT_IMAGES_UPLOAD, false, pairs)
                   ;

        // TODO fix _embedded issue
        List<UserReceiptImageResource> images = new ArrayList<>();
        for (ReceiptImage image : receipt.getImages()) {
            images.add(imageResourceAssembler.toResource(image));
        }
        resource.setImages(images);

        return resource;
    }

}
