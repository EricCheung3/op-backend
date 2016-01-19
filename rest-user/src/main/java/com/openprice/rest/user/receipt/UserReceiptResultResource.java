package com.openprice.rest.user.receipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.domain.receipt.ReceiptItemRepository;
import com.openprice.domain.receipt.ReceiptResult;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;

import lombok.Getter;
import lombok.Setter;

public class UserReceiptResultResource extends Resource<ReceiptResult> {

    @Getter @Setter
    private String storeName = "[Unknown]";

    @JsonInclude(Include.NON_EMPTY)
    @JsonProperty("_embedded")
    @Getter @Setter
    private Map<String, List<UserReceiptItemResource>> embeddedItems = new HashMap<>();

    public UserReceiptResultResource(final ReceiptResult resource) {
        super(resource);
    }

    @Component
    public static class Assembler implements ResourceAssembler<ReceiptResult, UserReceiptResultResource>, UserApiUrls {

        private final ReceiptItemRepository receiptItemRepository;
        private final UserReceiptItemResource.Assembler itemResourceAssembler;
        private final StoreChainRepository storeChainRepository;

        @Inject
        public Assembler(final ReceiptItemRepository receiptItemRepository,
                         final UserReceiptItemResource.Assembler itemResourceAssembler,
                         final StoreChainRepository storeChainRepository) {
            this.receiptItemRepository = receiptItemRepository;
            this.itemResourceAssembler = itemResourceAssembler;
            this.storeChainRepository = storeChainRepository;
        }

        @Override
        public UserReceiptResultResource toResource(final ReceiptResult result) {
            final String[] pairs = {"receiptId", result.getReceipt().getId()};
            final UserReceiptResultResource resource = new UserReceiptResultResource(result);
            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_USER_RECEIPTS_RECEIPT_RESULT, false, pairs)
                       .addLink("items", URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS, true, pairs)
                       .addLink("item", URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS_ITEM, false, pairs)
                       ;

            List<UserReceiptItemResource> items = new ArrayList<>();
            for (ReceiptItem item : receiptItemRepository.findByReceiptResultAndIgnoredIsFalseOrderByLineNumber(result)) {
                items.add(itemResourceAssembler.toResource(item));
            }
            resource.getEmbeddedItems().put("receiptItems", items);

            if (!StringUtils.isEmpty(result.getChainCode())) {
                final StoreChain chain = storeChainRepository.findByCode(result.getChainCode());
                resource.setStoreName(chain.getName());
            }
            return resource;
        }
    }


}
