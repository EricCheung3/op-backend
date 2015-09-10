package com.openprice.rest.admin.receipt;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.ReceiptImage;

@Component("admin_UserReceiptImageResourceAssembler")
public class UserReceiptImageResourceAssembler implements ResourceAssembler<ReceiptImage, UserReceiptImageResource> {

    @Override
    public UserReceiptImageResource toResource(final ReceiptImage receiptImage) {
        final UserReceiptImageResource resource = new UserReceiptImageResource(receiptImage);

        resource.add(linkTo(methodOn(UserReceiptRestController.class)
                                    .getUserReceiptImageById(receiptImage.getReceipt().getUser().getId(),
                                                             receiptImage.getReceipt().getId(),
                                                             receiptImage.getId()))
                .withSelfRel());

        resource.add(linkTo(methodOn(UserReceiptRestController.class)
                                    .downloadUserReceiptImage(receiptImage.getReceipt().getUser().getId(),
                                                              receiptImage.getReceipt().getId(),
                                                              receiptImage.getId()))
                .withRel(UserReceiptImageResource.LINK_NAME_DOWNLOAD));

        return resource;
    }

}
