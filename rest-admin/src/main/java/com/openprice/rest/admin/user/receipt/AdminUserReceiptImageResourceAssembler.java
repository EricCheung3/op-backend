package com.openprice.rest.admin.user.receipt;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.ReceiptImage;

@Component
public class AdminUserReceiptImageResourceAssembler implements ResourceAssembler<ReceiptImage, AdminUserReceiptImageResource> {

    @Override
    public AdminUserReceiptImageResource toResource(final ReceiptImage receiptImage) {
        final AdminUserReceiptImageResource resource = new AdminUserReceiptImageResource(receiptImage);

        resource.add(linkTo(methodOn(AdminUserReceiptRestController.class)
                                    .getUserReceiptImageById(receiptImage.getReceipt().getUser().getId(),
                                                             receiptImage.getReceipt().getId(),
                                                             receiptImage.getId()))
                .withSelfRel());

        resource.add(linkTo(methodOn(AdminUserReceiptRestController.class)
                                    .downloadUserReceiptImage(receiptImage.getReceipt().getUser().getId(),
                                                              receiptImage.getReceipt().getId(),
                                                              receiptImage.getId()))
                .withRel(AdminUserReceiptImageResource.LINK_NAME_DOWNLOAD));

        return resource;
    }

}
