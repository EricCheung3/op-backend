package com.openprice.rest.admin.user.receipt;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.admin.AdminApiUrls;

import lombok.Getter;
import lombok.Setter;

public class AdminUserReceiptResource extends Resource<Receipt> {

    @Getter @Setter
    private List<AdminUserReceiptImageResource> images;

    public AdminUserReceiptResource(final Receipt resource) {
        super(resource);
    }

    @Component
    public static class Assembler implements ResourceAssembler<Receipt, AdminUserReceiptResource>, AdminApiUrls {

        private final ReceiptImageRepository receiptImageRepository;
        private final AdminUserReceiptImageResource.Assembler imageResourceAssembler;

        @Inject
        public Assembler(final ReceiptImageRepository receiptImageRepository,
                         final AdminUserReceiptImageResource.Assembler imageResourceAssembler) {
            this.receiptImageRepository = receiptImageRepository;
            this.imageResourceAssembler = imageResourceAssembler;
        }

        @Override
        public AdminUserReceiptResource toResource(final Receipt receipt) {
            final String[] pairs = {"userId", receipt.getUser().getId(),
                                    "receiptId", receipt.getId()};
            final AdminUserReceiptResource resource = new AdminUserReceiptResource(receipt);
            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT, false, pairs)
                       .addLink("images", URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT_IMAGES,  true, pairs)
                       .addLink("image", URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT_IMAGES_IMAGE, false, pairs)
                       .addLink("user", URL_ADMIN_USERS_USER, false, pairs)
                       ;

            // Temp solution for embedded resources
            // Monitor https://github.com/spring-projects/spring-hateoas/issues/270
            List<AdminUserReceiptImageResource> images = new ArrayList<>();
            for (ReceiptImage image : receiptImageRepository.findByReceiptOrderByCreatedTime(receipt)) {
                images.add(imageResourceAssembler.toResource(image));
            }
            resource.setImages(images);

            return resource;
        }

    }

}
