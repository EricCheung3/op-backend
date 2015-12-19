package com.openprice.rest.user.receipt;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.domain.store.CatalogProduct;
import com.openprice.domain.store.CatalogProductRepository;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;

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

        private final StoreChainRepository chainRepository;
        private final CatalogProductRepository catalogProductRepository;

        @Inject
        public Assembler(final StoreChainRepository chainRepository,
                         final CatalogProductRepository catalogProductRepository) {
            this.chainRepository = chainRepository;
            this.catalogProductRepository = catalogProductRepository;
        }

        @Override
        public UserReceiptItemResource toResource(final ReceiptItem receiptItem) {
            final UserReceiptItemResource resource = new UserReceiptItemResource(receiptItem);

            if (StringUtils.hasText(receiptItem.getCatalogCode())) {
                final StoreChain chain = chainRepository.findByCode(receiptItem.getReceiptResult().getChainCode());
                if (chain != null) {
                    final CatalogProduct catalog = catalogProductRepository.findByChainAndCatalogCode(chain, receiptItem.getCatalogCode());
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
