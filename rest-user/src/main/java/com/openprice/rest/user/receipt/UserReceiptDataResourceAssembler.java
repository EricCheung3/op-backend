package com.openprice.rest.user.receipt;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.ReceiptData;
import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;

@Component
public class UserReceiptDataResourceAssembler implements ResourceAssembler<ReceiptData, UserReceiptDataResource>, UserApiUrls {

    private final UserReceiptItemResourceAssembler itemResourceAssembler;

    @Inject
    public UserReceiptDataResourceAssembler(final UserReceiptItemResourceAssembler itemResourceAssembler) {
        this.itemResourceAssembler = itemResourceAssembler;
    }

    @Override
    public UserReceiptDataResource toResource(final ReceiptData receiptData) {
        final String[] pairs = {"receiptId", receiptData.getReceipt().getId()};
        final UserReceiptDataResource resource = new UserReceiptDataResource(receiptData);
        final LinkBuilder linkBuilder = new LinkBuilder(resource);
        linkBuilder.addLink(Link.REL_SELF, URL_USER_RECEIPTS_RECEIPT_RESULT, false, pairs)
                   .addLink("items", URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS, true, pairs)
                   .addLink("item", URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS_ITEM, false, pairs)
                   ;

        // TODO fix _embedded issue
        List<UserReceiptItemResource> items = new ArrayList<>();
        for (ReceiptItem item : receiptData.getItems()) {
            if (!item.isIgnored()) {
                items.add(itemResourceAssembler.toResource(item));
            }
        }
        resource.setItems(items);

        return resource;
    }
}
