package com.openprice.rest.user.receipt;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.ReceiptImage;

@Component
public class UserReceiptImageResourceAssembler implements ResourceAssembler<ReceiptImage, UserReceiptImageResource> {

    @Override
    public UserReceiptImageResource toResource(final ReceiptImage receiptImage) {
        final UserReceiptImageResource resource = new UserReceiptImageResource(receiptImage);

        resource.add(linkTo(methodOn(UserReceiptImageRestController.class).getUserReceiptImageById(receiptImage.getReceipt().getId(), receiptImage.getId()))
                .withSelfRel());

        resource.add(linkTo(methodOn(UserReceiptRestController.class).getUserReceiptById(receiptImage.getReceipt().getId()))
                .withRel(UserReceiptImageResource.LINK_NAME_RECEIPT));

        resource.add(linkTo(methodOn(UserReceiptImageRestController.class).downloadUserReceiptImage(receiptImage.getReceipt().getId(), receiptImage.getId()))
                .withRel(UserReceiptImageResource.LINK_NAME_DOWNLOAD));

        resource.add(linkTo(methodOn(UserReceiptImageRestController.class).downloadUserReceiptImageAsBase64(receiptImage.getReceipt().getId(), receiptImage.getId()))
                .withRel(UserReceiptImageResource.LINK_NAME_BASE64));

        return resource;
    }

}
