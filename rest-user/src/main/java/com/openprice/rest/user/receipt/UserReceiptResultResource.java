package com.openprice.rest.user.receipt;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.ReceiptResult;
import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;

import lombok.Getter;
import lombok.Setter;

public class UserReceiptResultResource extends Resource<ReceiptResult> {

    @Getter @Setter
    private List<UserReceiptItemResource> items;

    public UserReceiptResultResource(final ReceiptResult resource) {
        super(resource);
    }

    @Component
    public static class Assembler implements ResourceAssembler<ReceiptResult, UserReceiptResultResource>, UserApiUrls {

        private final UserReceiptItemResource.Assembler itemResourceAssembler;

        @Inject
        public Assembler(final UserReceiptItemResource.Assembler itemResourceAssembler) {
            this.itemResourceAssembler = itemResourceAssembler;
        }

        @Override
        public UserReceiptResultResource toResource(final ReceiptResult receiptData) {
            final String[] pairs = {"receiptId", receiptData.getReceipt().getId()};
            final UserReceiptResultResource resource = new UserReceiptResultResource(receiptData);
            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_USER_RECEIPTS_RECEIPT_RESULT, false, pairs)
                       .addLink("items", URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS, true, pairs)
                       .addLink("item", URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS_ITEM, false, pairs)
                       ;

            // TODO fix _embedded issue
            List<UserReceiptItemResource> items = new ArrayList<>();
            for (ReceiptItem item : receiptData.getItems()) {
                if (!item.getIgnored()) {
                    items.add(itemResourceAssembler.toResource(item));
                }
            }
            resource.setItems(items);

            return resource;
        }
    }


}
