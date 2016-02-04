package com.openprice.rest.admin.receipt;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.ReceiptFeedback;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.admin.AdminApiUrls;

public class AdminReceiptFeedbackResource extends Resource<ReceiptFeedback>{

    public AdminReceiptFeedbackResource(final ReceiptFeedback receiptFeedback) {
        super(receiptFeedback);
    }
    
    @Component
    public static class Assembler implements ResourceAssembler<ReceiptFeedback, AdminReceiptFeedbackResource>, AdminApiUrls {
        
        @Override
        public AdminReceiptFeedbackResource toResource(final ReceiptFeedback feedback) {
            AdminReceiptFeedbackResource resource = new AdminReceiptFeedbackResource(feedback);
            
            final String[] pairs = {"receiptId", feedback.getReceipt().getId(),
                                    "feedbackId", feedback.getId()};

            final LinkBuilder linkbuilder = new LinkBuilder(resource);
            linkbuilder.addLink(Link.REL_SELF, URL_ADMIN_RECEIPTS_RECEIPT_FEEDBACKS_FEEDBACK, true, pairs)
                       .addLink("receipt", URL_ADMIN_RECEIPTS_RECEIPT, false, pairs)
                       ;
            
            return resource;
        }
        
    } 

}
