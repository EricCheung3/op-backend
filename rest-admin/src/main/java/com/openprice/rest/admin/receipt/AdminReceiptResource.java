package com.openprice.rest.admin.receipt;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.admin.AdminApiUrls;

import lombok.Getter;
import lombok.Setter;

public class AdminReceiptResource extends Resource<Receipt> {

    @Getter @Setter
    private String user;

    @Getter @Setter
    private String uploadTimestamp;

    @Getter @Setter
    private List<AdminReceiptImageResource> images;

    public AdminReceiptResource(final Receipt resource) {
        super(resource);
    }

    @Component
    public static class Assembler implements ResourceAssembler<Receipt, AdminReceiptResource>, AdminApiUrls {

        private final AdminReceiptImageResource.Assembler imageResourceAssembler;

        @Inject
        public Assembler(final AdminReceiptImageResource.Assembler imageResourceAssembler) {
            this.imageResourceAssembler = imageResourceAssembler;
        }

        @Override
        public AdminReceiptResource toResource(final Receipt receipt) {
            final AdminReceiptResource resource = new AdminReceiptResource(receipt);
            resource.setUser(receipt.getUser().getProfile().getDisplayName());
            // format uploaded timestamp
            LocalDateTime createdTime = receipt.getCreatedTime();
            resource.setUploadTimestamp(createdTime.format(DateTimeFormatter.ISO_DATE_TIME));

            final String[] pairs = {"receiptId", receipt.getId()};
            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_ADMIN_RECEIPTS_RECEIPT, false, pairs)
                       .addLink("images", URL_ADMIN_RECEIPTS_RECEIPT_IMAGES,  true, pairs)
                       .addLink("image", URL_ADMIN_RECEIPTS_RECEIPT_IMAGES_IMAGE, false, pairs)
                       .addLink("results", URL_ADMIN_RECEIPTS_RECEIPT_RESULTS,  true, pairs)
                       .addLink("result", URL_ADMIN_RECEIPTS_RECEIPT_RESULTS_RESULT, false, pairs)
                       ;

            // Temp solution for embedded resources
            // Monitor https://github.com/spring-projects/spring-hateoas/issues/270
            List<AdminReceiptImageResource> images = new ArrayList<>();
            for (ReceiptImage image : receipt.getImages()) {
                images.add(imageResourceAssembler.toResource(image));
            }
            resource.setImages(images);

            return resource;
        }

    }

}
