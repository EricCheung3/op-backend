package com.openprice.rest.user.receipt;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;
import com.openprice.store.CatalogProduct;
import com.openprice.store.StoreChain;
import com.openprice.store.StoreMetadata;

import lombok.Getter;
import lombok.Setter;

public class UserReceiptItemResource extends Resource<ReceiptItem> {

    @Getter @Setter
    private CatalogProduct catalog;

    public UserReceiptItemResource(ReceiptItem resource) {
        super(resource);
    }

    @Component
    public static class Assembler implements ResourceAssembler<ReceiptItem, UserReceiptItemResource>, UserApiUrls {

        private final StoreMetadata storeMetadata;

        @Inject
        public Assembler(final StoreMetadata storeMetadata) {
            this.storeMetadata = storeMetadata;
        }

        @Override
        public UserReceiptItemResource toResource(final ReceiptItem receiptItem) {
            final UserReceiptItemResource resource = new UserReceiptItemResource(receiptItem);

            if (StringUtils.hasText(receiptItem.getCatalogCode())) {
                final StoreChain storeChain = storeMetadata.getStoreChainByCode(receiptItem.getReceiptResult().getChainCode());
                if (storeChain != null) {
                    final CatalogProduct catalog = storeChain.getCatalogProductByCode(receiptItem.getCatalogCode());
                    if (catalog != null) {
                        resource.setCatalog(catalog);
                    }
                }
            }

            final String[] pairs = {"receiptId", receiptItem.getReceiptResult().getReceipt().getId(),
                                    "itemId", receiptItem.getId()};
            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS_ITEM, false, pairs)
                       .addLink("result", URL_USER_RECEIPTS_RECEIPT_RESULT, false, pairs)
                       .addLink("items", URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS, true, pairs)
                       ;
            return resource;
        }

    }

}
