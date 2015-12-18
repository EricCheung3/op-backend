package com.openprice.rest.admin.receipt;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.domain.store.CatalogProduct;
import com.openprice.domain.store.CatalogRepository;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.admin.AdminApiUrls;

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
        private final StoreChainRepository chainRepository;
        private final CatalogRepository catalogRepository;

        @Inject
        public Assembler(final StoreChainRepository chainRepository,
                         final CatalogRepository catalogRepository) {
            this.chainRepository = chainRepository;
            this.catalogRepository = catalogRepository;
        }

        @Override
        public AdminReceiptItemResource toResource(final ReceiptItem receiptItem) {
            final AdminReceiptItemResource resource = new AdminReceiptItemResource(receiptItem);

            if (StringUtils.hasText(receiptItem.getCatalogCode())) {
                final StoreChain chain = chainRepository.findByCode(receiptItem.getReceiptResult().getChainCode());
                if (chain != null) {
                    final CatalogProduct catalog = catalogRepository.findByChainAndCatalogCode(chain, receiptItem.getCatalogCode());
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
