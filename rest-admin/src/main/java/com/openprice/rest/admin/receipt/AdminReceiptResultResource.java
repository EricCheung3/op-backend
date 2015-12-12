package com.openprice.rest.admin.receipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.core.Relation;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.domain.receipt.ReceiptItemRepository;
import com.openprice.domain.receipt.ReceiptResult;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.admin.AdminApiUrls;

import lombok.Getter;
import lombok.Setter;

@Relation(value = "result", collectionRelation = "results")
public class AdminReceiptResultResource extends Resource<ReceiptResult> {

    @JsonInclude(Include.NON_EMPTY)
    @JsonProperty("_embedded")
    @Getter @Setter
    private Map<String, List<AdminReceiptItemResource>> embeddedItems = new HashMap<>();

    AdminReceiptResultResource(final ReceiptResult resource) {
        super(resource);
    }

    @Component
    public static class Assembler implements ResourceAssembler<ReceiptResult, AdminReceiptResultResource>, AdminApiUrls {

        private final ReceiptItemRepository receiptItemRepository;
        private final AdminReceiptItemResource.Assembler itemResourceAssembler;

        @Inject
        public Assembler(final ReceiptItemRepository receiptItemRepository,
                         final AdminReceiptItemResource.Assembler itemResourceAssembler) {
            this.receiptItemRepository = receiptItemRepository;
            this.itemResourceAssembler = itemResourceAssembler;
        }

        @Override
        public AdminReceiptResultResource toResource(final ReceiptResult result) {
            final AdminReceiptResultResource resource = new AdminReceiptResultResource(result);

            List<AdminReceiptItemResource> items = new ArrayList<>();
            for (ReceiptItem item : receiptItemRepository.findByReceiptResultOrderByLineNumber(result)) {
                items.add(itemResourceAssembler.toResource(item));
            }
            resource.getEmbeddedItems().put("receiptItems", items);

            final String[] pairs = {"receiptId", result.getReceipt().getId(),
                                    "resultId", result.getId()};
            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT, false, pairs)
                       .addLink("items", URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT_ITEMS, true, pairs)
                       .addLink("item", URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT_ITEMS_ITEM, false, pairs)
                       ;

            return resource;
        }
    }
}
