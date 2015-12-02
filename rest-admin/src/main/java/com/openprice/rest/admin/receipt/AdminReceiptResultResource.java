package com.openprice.rest.admin.receipt;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.ReceiptData;
import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.admin.AdminApiUrls;

import lombok.Getter;
import lombok.Setter;

public class AdminReceiptResultResource extends Resource<ReceiptData> {

    @Getter @Setter
    private List<AdminReceiptItemResource> items;

    AdminReceiptResultResource(final ReceiptData resource) {
        super(resource);
    }

    @Component
    public static class Assembler implements ResourceAssembler<ReceiptData, AdminReceiptResultResource>, AdminApiUrls {

        private final AdminReceiptItemResource.Assembler itemResourceAssembler;

        @Inject
        public Assembler(final AdminReceiptItemResource.Assembler itemResourceAssembler) {
            this.itemResourceAssembler = itemResourceAssembler;
        }

        @Override
        public AdminReceiptResultResource toResource(final ReceiptData receiptData) {
            final String[] pairs = {"receiptId", receiptData.getReceipt().getId(),
                                    "resultId", receiptData.getId()};
            final AdminReceiptResultResource resource = new AdminReceiptResultResource(receiptData);
            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT, false, pairs)
                       .addLink("items", URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT_ITEMS, true, pairs)
                       .addLink("item", URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT_ITEMS_ITEM, false, pairs)
                       ;

            // TODO fix _embedded issue
            List<AdminReceiptItemResource> items = new ArrayList<>();
            for (ReceiptItem item : receiptData.getItems()) {
                if (!item.isIgnored()) {
                    items.add(itemResourceAssembler.toResource(item));
                }
            }
            resource.setItems(items);

            return resource;
        }
    }
}
