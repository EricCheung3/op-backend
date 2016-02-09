package com.openprice.rest.admin.receipt;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.ReceiptField;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.admin.AdminApiUrls;

public class AdminReceiptFieldResource extends Resource<ReceiptField> {

    public AdminReceiptFieldResource(ReceiptField receiptField) {
        super(receiptField);
    }

    @Component
    public static class Assembler implements ResourceAssembler<ReceiptField, AdminReceiptFieldResource>, AdminApiUrls {

        @Override
        public AdminReceiptFieldResource toResource(final ReceiptField receiptField) {
            AdminReceiptFieldResource resource = new AdminReceiptFieldResource(receiptField);

            final String[] pairs = {"receiptId", receiptField.getReceiptResult().getReceipt().getId(),
                                    "resultId", receiptField.getReceiptResult().getId(),
                                    "fieldId", receiptField.getId()};

            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT_FIELDS_FIELD, false, pairs)
                       .addLink("result", URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT, false, pairs)
                       .addLink("items", URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT_ITEMS, true, pairs);

            return resource;
        }

    }

}
