package com.openprice.rest.user.receipt;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;

@Component
public class UserReceiptItemResourceAssembler implements ResourceAssembler<ReceiptItem, UserReceiptItemResource>, UserApiUrls {

    public static final String LINK_NAME_PARSER_RESULT = "result";
    public static final String LINK_NAME_ITEMS = "items";

    @Override
    public UserReceiptItemResource toResource(final ReceiptItem receiptItem) {

        final String[] pairs = {"receiptId", receiptItem.getReceiptData().getReceipt().getId(), "itemId", receiptItem.getId()};
        final UserReceiptItemResource resource = new UserReceiptItemResource(receiptItem);
        final LinkBuilder linkBuilder = new LinkBuilder(resource);
        linkBuilder.addLink(Link.REL_SELF, URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS_ITEM, false, pairs)
                   .addLink(LINK_NAME_PARSER_RESULT, URL_USER_RECEIPTS_RECEIPT_RESULT, false, pairs)
                   .addLink(LINK_NAME_ITEMS, URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS, true, pairs)
                   ;
        return resource;
    }

}
