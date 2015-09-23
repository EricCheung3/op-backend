package com.openprice.rest.admin.receipt;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.ReceiptImage;

@Component
public class AdminReceiptImageResourceAssembler implements ResourceAssembler<ReceiptImage, AdminReceiptImageResource> {

    @Override
    public AdminReceiptImageResource toResource(final ReceiptImage receiptImage) {
        final AdminReceiptImageResource resource = new AdminReceiptImageResource(receiptImage);

        resource.add(linkTo(methodOn(AdminReceiptRestController.class)
                                    .getReceiptImageById(receiptImage.getReceipt().getId(), receiptImage.getId()))
                .withSelfRel());

        resource.add(linkTo(methodOn(AdminReceiptRestController.class)
                                    .getReceiptById(receiptImage.getReceipt().getId()))
                .withRel(AdminReceiptImageResource.LINK_NAME_RECEIPT));

        resource.add(linkTo(methodOn(AdminReceiptRestController.class)
                                    .downloadReceiptImage(receiptImage.getReceipt().getId(), receiptImage.getId()))
                .withRel(AdminReceiptImageResource.LINK_NAME_DOWNLOAD));

        resource.add(linkTo(methodOn(AdminReceiptRestController.class)
                                    .downloadUserReceiptImageAsBase64(receiptImage.getReceipt().getId(), receiptImage.getId()))
                .withRel(AdminReceiptImageResource.LINK_NAME_BASE64));

        return resource;
    }

}
