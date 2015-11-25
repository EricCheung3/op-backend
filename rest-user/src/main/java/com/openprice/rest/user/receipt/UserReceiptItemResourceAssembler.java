package com.openprice.rest.user.receipt;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.domain.store.Catalog;
import com.openprice.domain.store.CatalogRepository;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;

@Component
public class UserReceiptItemResourceAssembler implements ResourceAssembler<ReceiptItem, UserReceiptItemResource>, UserApiUrls {

    private final StoreChainRepository chainRepository;
    private final CatalogRepository catalogRepository;

    @Inject
    public UserReceiptItemResourceAssembler(final StoreChainRepository chainRepository,
                                            final CatalogRepository catalogRepository) {
        this.chainRepository = chainRepository;
        this.catalogRepository = catalogRepository;
    }

    @Override
    public UserReceiptItemResource toResource(final ReceiptItem receiptItem) {
        final UserReceiptItemResource resource = new UserReceiptItemResource(receiptItem);

        if (StringUtils.hasText(receiptItem.getCatalogCode())) {
            final StoreChain chain = chainRepository.findByCode(receiptItem.getReceiptData().getChainCode());
            if (chain != null) {
                final Catalog catalog = catalogRepository.findByChainAndCode(chain, receiptItem.getCatalogCode());
                if (catalog != null) {
                    resource.setLabelCodes(catalog.getLabelCodes());
                }
            }
        }

        final String[] pairs = {"receiptId", receiptItem.getReceiptData().getReceipt().getId(),
                                "itemId", receiptItem.getId()};
        final LinkBuilder linkBuilder = new LinkBuilder(resource);
        linkBuilder.addLink(Link.REL_SELF, URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS_ITEM, false, pairs)
                   .addLink("result", URL_USER_RECEIPTS_RECEIPT_RESULT, false, pairs)
                   .addLink("items", URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS, true, pairs)
                   ;
        return resource;
    }

}
