package com.openprice.rest.admin.receipt;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.admin.AdminApiUrls;
import com.openprice.store.CatalogProduct;
import com.openprice.store.StoreChain;
import com.openprice.store.StoreMetadata;

import lombok.Getter;
import lombok.Setter;

public class AdminReceiptItemResource extends Resource<ReceiptItem> {

    @Getter @Setter
    private Integer lineNumber;

    @Getter @Setter
    private Boolean userDeleted;

    @Getter @Setter
    private CatalogProduct catalog;

    public AdminReceiptItemResource(ReceiptItem resource) {
        super(resource);
    }

    @Component
    public static class Assembler implements ResourceAssembler<ReceiptItem, AdminReceiptItemResource>, AdminApiUrls {
        private final StoreMetadata storeMetadata;

        @Inject
        public Assembler(final StoreMetadata storeMetadata) {
            this.storeMetadata = storeMetadata;
        }

        @Override
        public AdminReceiptItemResource toResource(final ReceiptItem receiptItem) {
            final AdminReceiptItemResource resource = new AdminReceiptItemResource(receiptItem);

            if (StringUtils.hasText(receiptItem.getCatalogCode())) {
                final StoreChain storeChain = storeMetadata.getStoreChainByCode(receiptItem.getReceiptResult().getChainCode());
                if (storeChain != null) {
                    final CatalogProduct catalog = storeChain.getCatalogProductByCode(receiptItem.getCatalogCode());
                    if (catalog != null) {
                        resource.setCatalog(catalog);
                    }
                }
            }

            // set properties admin can see
            resource.setLineNumber(receiptItem.getLineNumber());
            resource.setUserDeleted(receiptItem.getIgnored());

            final String[] pairs = {"receiptId", receiptItem.getReceiptResult().getReceipt().getId(),
                                    "resultId", receiptItem.getReceiptResult().getId(),
                                    "itemId", receiptItem.getId()};
            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT_ITEMS_ITEM, false, pairs)
                       .addLink("result", URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT, false, pairs)
                       .addLink("items", URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT_ITEMS, true, pairs)
                       ;

            return resource;
        }

    }
}
