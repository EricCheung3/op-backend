package com.openprice.rest.user.receipt;

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
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;

import lombok.Getter;

@Relation(value = "receipt") // TODO why doing this?
public class UserReceiptResource extends Resource<Receipt> {

    @JsonInclude(Include.NON_EMPTY)
    @JsonProperty("_embedded")
    @Getter
    private Map<String, List<UserReceiptImageResource>> embeddedImages = new HashMap<>();

    public UserReceiptResource(final Receipt resource) {
        super(resource);
    }

    @Component
    public static class Assembler implements ResourceAssembler<Receipt, UserReceiptResource>, UserApiUrls {

        private final ReceiptImageRepository receiptImageRepository;
        private final UserReceiptImageResource.Assembler imageResourceAssembler;

        @Inject
        public Assembler(final ReceiptImageRepository receiptImageRepository,
                         final UserReceiptImageResource.Assembler imageResourceAssembler) {
            this.receiptImageRepository = receiptImageRepository;
            this.imageResourceAssembler = imageResourceAssembler;
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

            List<UserReceiptImageResource> images = new ArrayList<>();
            for (ReceiptImage image : receiptImageRepository.findByReceiptOrderByCreatedTime(receipt)) {
                images.add(imageResourceAssembler.toResource(image));
            }
            resource.getEmbeddedImages().put("receiptImages", images);

            return resource;
        }

    }

}
