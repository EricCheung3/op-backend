package com.openprice.rest.user.receipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.domain.receipt.ReceiptItemRepository;
import com.openprice.domain.receipt.ReceiptResult;
import com.openprice.domain.receipt.ReceiptResultRepository;
import com.openprice.domain.receipt.ReceiptStatusType;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;
import com.openprice.store.StoreChain;
import com.openprice.store.StoreMetadata;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

public class UserReceiptResource extends Resource<Receipt> {

    @Getter @Setter
    private String chainCode = "generic";

    @Getter @Setter
    private String icon = "generic";

    @Getter @Setter
    private String storeName = "[Unknown]";

    @Getter @Setter
    private String total;

    @JsonInclude(Include.NON_EMPTY)
    @JsonProperty("_embedded")
    @Getter
    private Map<String, List<? extends ResourceSupport>> embedded = new HashMap<>();

    public UserReceiptResource(final Receipt resource) {
        super(resource);
    }

    @Component
    @Slf4j
    public static class Assembler implements ResourceAssembler<Receipt, UserReceiptResource>, UserApiUrls {

        private final StoreMetadata storeMetadata;
        private final ReceiptResultRepository receiptResultRepository;
        private final ReceiptItemRepository receiptItemRepository;
        private final UserReceiptItemResource.Assembler itemResourceAssembler;

        @Inject
        public Assembler(final StoreMetadata storeMetadata,
                         final ReceiptResultRepository receiptResultRepository,
                         final ReceiptItemRepository receiptItemRepository,
                         final UserReceiptItemResource.Assembler itemResourceAssembler) {
            this.storeMetadata = storeMetadata;
            this.receiptResultRepository = receiptResultRepository;
            this.receiptItemRepository = receiptItemRepository;
            this.itemResourceAssembler = itemResourceAssembler;
        }

        @Override
        public UserReceiptResource toResource(final Receipt receipt) {
            final String[] pairs = {"receiptId", receipt.getId()};
            final UserReceiptResource resource = new UserReceiptResource(receipt);
            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_USER_RECEIPTS_RECEIPT, false, pairs)
                       .addLink("images",URL_USER_RECEIPTS_RECEIPT_IMAGES,  true, pairs)
                       .addLink("image", URL_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE, false, pairs)
                       .addLink("result", URL_USER_RECEIPTS_RECEIPT_RESULT, false, pairs)
                       .addLink("items", URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS, true, pairs)
                       .addLink("item", URL_USER_RECEIPTS_RECEIPT_RESULT_ITEMS_ITEM, false, pairs)
                       .addLink("feedback", URL_USER_RECEIPTS_RECEIPT_FEEDBACK, false, pairs)
                       .addLink("upload", URL_USER_RECEIPTS_RECEIPT_IMAGES_UPLOAD, false, pairs)
                       ;

            // load result
            if (receipt.getStatus() == ReceiptStatusType.HAS_RESULT) {
                final ReceiptResult result = receiptResultRepository.findFirstByReceiptOrderByCreatedTimeDesc(receipt);
                if (result == null) {
                    log.error("SEVERE: no result found when receipt {} is in HAS_RESULT status!", receipt.getId());
                } else {
                    final List<UserReceiptItemResource> items = new ArrayList<>();
                    for (ReceiptItem item : receiptItemRepository.findByReceiptResultAndIgnoredIsFalseOrderByLineNumber(result)) {
                        items.add(itemResourceAssembler.toResource(item));
                    }
                    resource.getEmbedded().put("receiptItems", items);

                    final String chainCode = result.getChainCode();
                    if (!StringUtils.isEmpty(chainCode)) {
                        final StoreChain chain = storeMetadata.getStoreChainByCode(chainCode);
                        if (chain != null) {
                            resource.setStoreName(chain.getName());
                            resource.setChainCode(chainCode);
                            resource.setIcon(chain.getIcon());
                        }
                    }

                    resource.setTotal(result.getParsedTotal());
                }
            }

            return resource;
        }

    }

}
